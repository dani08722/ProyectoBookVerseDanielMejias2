package com.salesianostriana.dam.proyectobookversedanielmejias2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.proyectobookversedanielmejias2.repository.ClienteRepository;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.LibroService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final ClienteRepository clienteRepo;
	private final LibroService libroService;

	@GetMapping({"/", "/index"})
	public String index(Model model) {
		model.addAttribute("Clientes", clienteRepo.findAll());
		model.addAttribute("librosDestacados", libroService.findAll());
		return "index";
	}

	@GetMapping("/acceso-denegado")
	public String accesoDenegado() {
		return "acceso-denegado";
	}

	@GetMapping("/registro")
	public String registroNoDisponible() {
		return "registro";
	}

}
