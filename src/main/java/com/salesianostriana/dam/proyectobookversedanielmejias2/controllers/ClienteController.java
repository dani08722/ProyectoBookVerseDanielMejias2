package com.salesianostriana.dam.proyectobookversedanielmejias2.controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Cliente;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.User;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.UserRole;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.ClienteService;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.PedidoService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Controller @RequiredArgsConstructor
public class ClienteController {

	private final ClienteService clienteService;
	private final PedidoService pedidoService;
	private final PasswordEncoder passwordEncoder;
	
	
	@GetMapping("/admin/clientes")
	public String listarClientes(Model model) {
		model.addAttribute("clientes", clienteService.findAll());
		return "admin/lista-clientes";
	}
	

	@GetMapping("/admin/clientes/crear")
	public String mostrarFormularioCrear(Model model) {
		Cliente cliente = new Cliente();
		cliente.setActivo(true);
		cliente.setUser(User.builder()
				.role(UserRole.USER)
				.build());
		model.addAttribute("cliente", cliente);
		return "admin/form-cliente";
	}

	@GetMapping("/admin/clientes/editar/{id}")
	public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
		return clienteService.findById(id)
				.map(cliente -> {
					if (cliente.getUser() == null) {
						cliente.setUser(User.builder()
								.role(UserRole.USER)
								.build());
					}
					model.addAttribute("cliente", cliente);
					return "admin/form-cliente";
				})
				.orElse("redirect:/admin/clientes");
	}

	
	@PostMapping("/admin/clientes/submit")
	public String crearCliente(@ModelAttribute("cliente") Cliente cliente) {
		if (cliente.getIdCliente() == null) {
			cliente.setFechaRegistro(LocalDate.now());
			cliente.setActivo(true);
		} else {
			clienteService.findById(cliente.getIdCliente())
					.ifPresent(clienteExistente -> {
						cliente.setFechaRegistro(clienteExistente.getFechaRegistro());
						cliente.setActivo(clienteExistente.isActivo());

						if (clienteExistente.getUser() != null && cliente.getUser() != null) {
							cliente.getUser().setRole(clienteExistente.getUser().getRole());

							if (cliente.getUser().getPassword() == null || cliente.getUser().getPassword().isBlank()) {
								cliente.getUser().setPassword(clienteExistente.getUser().getPassword());
							}
						}
					});
		}
		prepararUsuario(cliente);
		clienteService.save(cliente);
		return "redirect:/admin/clientes";
	}

	@Transactional
	@PostMapping("/admin/clientes/eliminar/{id}")
	public String eliminarCliente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		clienteService.findById(id)
				.ifPresent(cliente -> {
					//Si no es admin se elimina
					if (cliente.getUser().getRole() != UserRole.ADMIN) {
						new ArrayList<>(cliente.getPedidos()).forEach(pedidoService::delete);
						clienteService.delete(cliente);
						redirectAttributes.addFlashAttribute("mensajeExito", "Cliente eliminado correctamente.");
					} else {
						redirectAttributes.addFlashAttribute("mensajeError", "No se puede eliminar un cliente administrador.");
					}
				});

		return "redirect:/admin/clientes";
	}

	
	private void prepararUsuario(Cliente cliente) {
		User user = cliente.getUser();
		
		//Si el cliente no tiene un usuario asociado, se crea una nueva instancia de User y se vincula al cliente.
		if (user == null) {
			user = new User();
			cliente.setUser(user);
		}
		
		if (user.getRole() == null) {
			user.setRole(UserRole.USER);
		}
		
		//Setea el cliente en user para asegurar que estan relacionados
		user.setCliente(cliente);

		String password = user.getPassword();
		
		//Primero se asegura de que haya contraseña y que la contraseña no esté ya encriptada
		//para luego encriptarla
		if (password != null && !password.isBlank() && !password.startsWith("{")) {
			user.setPassword(passwordEncoder.encode(password));
		}
	}
}
