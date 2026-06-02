package com.salesianostriana.dam.proyectobookversedanielmejias2.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.salesianostriana.dam.proyectobookversedanielmejias2.exception.LibroDuplicadoException;
import com.salesianostriana.dam.proyectobookversedanielmejias2.exception.LibroNoEliminableException;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Libro;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.LibroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller @RequiredArgsConstructor
public class LibroController {

	private final LibroService libroService;
	
	
	@GetMapping("/catalogo")
    public String listarTodos(
            @RequestParam(required = false) String texto,
            Model model) {

        model.addAttribute("libros", libroService.buscarPorTexto(texto));
        model.addAttribute("textoBusqueda", texto);

        return "catalogo";
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
		model.addAttribute("modoEdicion", false);
		return "admin/form-libro";
	}
	
	
	
	@PostMapping("/admin/libros/submit")
		public String crearLibro(@Valid @ModelAttribute("libro") Libro libro,
				BindingResult bindingResult,
				@RequestParam(defaultValue = "false") boolean modoEdicion,
				Model model) {

			if (bindingResult.hasErrors()) {
				model.addAttribute("modoEdicion", modoEdicion);
				return "admin/form-libro";
			}

			try {
				if (modoEdicion) {
					libroService.save(libro);
				} else {
					libroService.crearLibro(libro);
				}
			} catch (LibroDuplicadoException ex) {
				model.addAttribute("mensajeError", ex.getMessage());
				model.addAttribute("modoEdicion", false);
				return "admin/form-libro";
			}
			
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
			model.addAttribute("modoEdicion", true);
			return "admin/form-libro";
		}

		return "redirect:/admin/libros";
	}

	
	
	@PostMapping("/admin/libros/eliminar/{isbn}")
	public String eliminarLibro(@PathVariable String isbn, RedirectAttributes redirectAttributes) {
		try {
			libroService.eliminarLibro(isbn);
			redirectAttributes.addFlashAttribute("mensajeExito", "Libro eliminado correctamente.");
		} catch (LibroNoEliminableException ex) {
			redirectAttributes.addFlashAttribute("mensajeError", ex.getMessage());
		}

		return "redirect:/admin/libros";
	}

}
