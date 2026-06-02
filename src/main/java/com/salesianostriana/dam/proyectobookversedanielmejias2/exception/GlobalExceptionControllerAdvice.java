package com.salesianostriana.dam.proyectobookversedanielmejias2.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionControllerAdvice {

	@ExceptionHandler(LibroNoEncontradoException.class)
	public String handleLibroNoEncontradoException(LibroNoEncontradoException ex, Model model) {
		model.addAttribute("titulo", "Libro no encontrado");
		model.addAttribute("mensaje", ex.getMessage());
		model.addAttribute("urlVolver", "/catalogo");
		model.addAttribute("textoVolver", "Volver al catálogo");
		return "error";
	}
	
	@ExceptionHandler(StockInsuficienteException.class)
	public String handleStockInsuficienteException(StockInsuficienteException ex, Model model) {
		model.addAttribute("titulo", "Stock insuficiente");
		model.addAttribute("mensaje", ex.getMessage());
		model.addAttribute("urlVolver", "/carrito");
		model.addAttribute("textoVolver", "Volver al carrito");
		return "error";
	}
}
