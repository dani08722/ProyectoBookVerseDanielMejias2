package com.salesianostriana.dam.proyectobookversedanielmejias2.controllers;

import java.util.NoSuchElementException;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.WebApplicationContext;

import com.salesianostriana.dam.proyectobookversedanielmejias2.services.CarritoCompraService;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.LibroService;

import lombok.RequiredArgsConstructor;

@Controller @RequiredArgsConstructor
@Scope (value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CarritoCompraController {

	private final LibroService libroService;
	
	private final CarritoCompraService carritoCompraService;
	
	@GetMapping ("/carrito")
    public String showCarrito (Model model) {
    	
    	if (model.addAttribute("products",carritoCompraService.getProductosCarrito()) == null) 
    			return "redirect:/";   	
    		
    	return "carrito";
    }
	
	
	
	
}
