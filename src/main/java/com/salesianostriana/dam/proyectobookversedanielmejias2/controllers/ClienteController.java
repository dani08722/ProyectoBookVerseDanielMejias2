package com.salesianostriana.dam.proyectobookversedanielmejias2.controllers;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Cliente;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.ClienteService;

import lombok.RequiredArgsConstructor;

@Controller @RequiredArgsConstructor
public class ClienteController {

	private final ClienteService clienteService;
	
	@GetMapping("/admin/clientes")
	public String listarClientes(Model model) {
		model.addAttribute("clientes", clienteService.findAll());
		return "admin/lista-clientes";
	}

	@GetMapping("/admin/clientes/crear")
	public String mostrarFormularioCrear(Model model) {
		Cliente cliente = new Cliente();
		cliente.setActivo(true);
		model.addAttribute("cliente", cliente);
		return "admin/form-cliente";
	}

	
	@PostMapping("/admin/clientes/submit")
	public String crearCliente(@ModelAttribute("cliente") Cliente cliente) {
		cliente.setFechaRegistro(LocalDate.now());
		clienteService.save(cliente);
		return "redirect:/admin/clientes";
	}


}
