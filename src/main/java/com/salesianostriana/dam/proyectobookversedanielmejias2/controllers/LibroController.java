package com.salesianostriana.dam.proyectobookversedanielmejias2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.proyectobookversedanielmejias2.repository.LibroRepository;

import lombok.RequiredArgsConstructor;

@Controller @RequiredArgsConstructor
public class LibroController {

	private final LibroRepository libroRepo;
	
	@GetMapping("/catalogo")
    public String listarTodos(Model model) {
        // Buscamos todos los libros en la base de datos
        model.addAttribute("libros", libroRepo.findAll());
        return "catalogo"; // Nombre del archivo HTML
    }
}
