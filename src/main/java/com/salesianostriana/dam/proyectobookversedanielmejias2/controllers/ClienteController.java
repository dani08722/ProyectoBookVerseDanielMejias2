package com.salesianostriana.dam.proyectobookversedanielmejias2.controllers;
import com.salesianostriana.dam.proyectobookversedanielmejias2.repository.ClienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller @RequiredArgsConstructor
public class ClienteController {

	private final ClienteRepository clienteRepository;

	@GetMapping("/index")
	public String prueba(Model model) {
		model.addAttribute("Clientes", clienteRepository.findAll());
		return "index";
	}
	
	
}
