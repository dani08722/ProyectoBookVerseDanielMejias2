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
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.LineaPedidoService;

import lombok.RequiredArgsConstructor;

@Controller @RequiredArgsConstructor
public class LibroController {

	private final LibroService libroService;
	private final LineaPedidoService lineaPedidoService;
	
	@GetMapping("/catalogo")
    public String listarTodos(Model model) {
        // Buscamos todos los libros en la base de datos
        model.addAttribute("libros", libroService.findAll());
        return "catalogo"; // Nombre del archivo HTML
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

	@PostMapping("/admin/libros/submit")
	public String crearLibro(@ModelAttribute("libro") Libro libro) {
		libroService.save(libro);
		return "redirect:/admin/libros";
	}

	
	@PostMapping("/admin/libros/eliminar/{isbn}")
	public String eliminarLibro(@PathVariable String isbn) {
		
		Optional<Libro> libro = libroService.findById(isbn);

		if (libro.isPresent()) {
			lineaPedidoService.deleteByIsbn(isbn);
			libroService.delete(libro.get());
		}

		return "redirect:/admin/libros";
	}

}
