package com.salesianostriana.dam.proyectobookversedanielmejias2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.proyectobookversedanielmejias2.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final ClienteRepository clienteRepo;

	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("Clientes", clienteRepo.findAll());
		return "index";
	}

}
