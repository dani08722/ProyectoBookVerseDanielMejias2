package com.salesianostriana.dam.proyectobookversedanielmejias2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.proyectobookversedanielmejias2.services.ClienteService;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.LibroService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private static final int NUM_PRODUCTOS_ALEATORIOS = 4;

	private final ClienteService clienteService;
	private final LibroService libroService;

	
	
	@GetMapping({"/", "/index"})
	public String index(Model model) {
		model.addAttribute("Clientes", clienteService.findAll());
		model.addAttribute("librosDestacados", libroService.obtenerLibrosAleatorios(NUM_PRODUCTOS_ALEATORIOS));
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
