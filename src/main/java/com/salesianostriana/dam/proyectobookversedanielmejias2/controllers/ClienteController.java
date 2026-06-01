package com.salesianostriana.dam.proyectobookversedanielmejias2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.salesianostriana.dam.proyectobookversedanielmejias2.exception.ClienteNoEliminableException;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Cliente;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.ClienteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ClienteController {

	private final ClienteService clienteService;

	
	
	@GetMapping("/admin/clientes")
	public String listarClientes(Model model) {
		model.addAttribute("clientes", clienteService.findAll());
		return "admin/lista-clientes";
	}
	
	

	@GetMapping("/admin/clientes/crear")
	public String mostrarFormularioCrear(Model model) {
		model.addAttribute("cliente", clienteService.nuevoCliente());
		return "admin/form-cliente";
	}

	
	
	@GetMapping("/admin/clientes/{id}")
	public String verDetalleCliente(@PathVariable Long id, Model model) {
		return clienteService.findById(id)
				.map(cliente -> {
					model.addAttribute("cliente", cliente);
					return "admin/detalle-cliente";
				})
				.orElse("redirect:/admin/clientes");
	}

	
	
	@GetMapping("/admin/clientes/editar/{id}")
	public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
		return clienteService.findById(id)
				.map(cliente -> {
					model.addAttribute("cliente", clienteService.prepararClienteParaFormulario(cliente));
					return "admin/form-cliente";
				})
				.orElse("redirect:/admin/clientes");
	}

	
	
	@PostMapping("/admin/clientes/submit")
	public String crearCliente(@ModelAttribute("cliente") Cliente cliente) {
		clienteService.guardarCliente(cliente);
		return "redirect:/admin/clientes";
	}

	
	
	@PostMapping("/admin/clientes/eliminar/{id}")
	public String eliminarCliente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		try {
			clienteService.eliminarCliente(id);
			redirectAttributes.addFlashAttribute("mensajeExito", "Cliente eliminado correctamente.");
		} catch (ClienteNoEliminableException ex) {
			redirectAttributes.addFlashAttribute("mensajeError", ex.getMessage());
		}

		return "redirect:/admin/clientes";
	}

}
