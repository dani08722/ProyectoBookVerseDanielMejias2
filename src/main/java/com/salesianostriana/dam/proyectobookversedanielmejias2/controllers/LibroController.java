package com.salesianostriana.dam.proyectobookversedanielmejias2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Libro;
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

	@GetMapping("/admin/libros/crear")
	public String mostrarFormularioCrear(Model model) {
		model.addAttribute("libro", new Libro());
		return "admin/form-libro";
	}

	@GetMapping("/admin/libros")
	public String listarLibrosAdmin(Model model) {
		model.addAttribute("libros", libroRepo.findAll());
		return "admin/lista-libros";
	}

	@PostMapping("/admin/libros/submit")
	public String crearLibro(@ModelAttribute("libro") Libro libro) {
		libroRepo.save(libro);
		return "redirect:/admin/libros";
	}

}
