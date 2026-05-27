package com.salesianostriana.dam.proyectobookversedanielmejias2.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Libro;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.LibroService;

import lombok.RequiredArgsConstructor;

@Controller @RequiredArgsConstructor
public class LibroController {

	private final LibroService libroService;
	
	
	@GetMapping("/catalogo")
    public String listarTodos(Model model) {
        
        model.addAttribute("libros", libroService.findAll());
        return "catalogo"; //
    }

	
	@GetMapping("/libros/{isbn}")
	public String verDetalleLibro(@PathVariable String isbn, Model model) {
		
		Optional<Libro> libro = libroService.findById(isbn);

		if (libro.isPresent()) {
			model.addAttribute("libro", libro.get());
			return "detalle-libro";
		}

		return "redirect:/catalogo";
	}
	

	
	@GetMapping("/admin/libros/crear")
	public String mostrarFormularioCrear(Model model) {
		model.addAttribute("libro", new Libro());
		return "admin/form-libro";
	}
	
	
	
	@PostMapping("/admin/libros/submit")
		public String crearLibro(@ModelAttribute("libro") Libro libro) {
			libroService.save(libro);
			return "redirect:/admin/libros";
	}
	
			
	
	@GetMapping("/admin/libros")
	public String listarLibrosAdmin(Model model) {
		model.addAttribute("libros", libroService.findAll());
		return "admin/lista-libros";
	}

	
	
	@GetMapping("/admin/libros/editar/{isbn}")
	public String mostrarFormularioEditar(@PathVariable String isbn, Model model) {
		
		Optional<Libro> libro = libroService.findById(isbn);

		if (libro.isPresent()) {
			model.addAttribute("libro", libro.get());
			return "admin/form-libro";
		}

		return "redirect:/admin/libros";
	}

	
	
	@PostMapping("/admin/libros/eliminar/{isbn}")
	public String eliminarLibro(@PathVariable String isbn) {
		libroService.eliminarLibro(isbn);
		return "redirect:/admin/libros";
	}

}
