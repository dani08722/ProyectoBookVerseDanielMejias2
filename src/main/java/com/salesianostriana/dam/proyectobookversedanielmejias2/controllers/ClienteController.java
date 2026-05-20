package com.salesianostriana.dam.proyectobookversedanielmejias2.controllers;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Cliente;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.User;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.UserRole;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.ClienteService;

import lombok.RequiredArgsConstructor;

@Controller @RequiredArgsConstructor
public class ClienteController {

	private final ClienteService clienteService;
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

	
	@PostMapping("/admin/clientes/submit")
	public String crearCliente(@ModelAttribute("cliente") Cliente cliente) {
		cliente.setFechaRegistro(LocalDate.now());
		prepararUsuario(cliente);
		clienteService.save(cliente);
		return "redirect:/admin/clientes";
	}

	
	private void prepararUsuario(Cliente cliente) {
		User user = cliente.getUser();
		
		//Si el cliente no tiene un usuario asociado, se crea una nueva instancia de User y se vincula al cliente.
		if (user == null) {
			user = new User();
			cliente.setUser(user);
		}
		
		//Si el usuario no tiene ningún rol asignado , se le otorga user como predeterminado.
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
