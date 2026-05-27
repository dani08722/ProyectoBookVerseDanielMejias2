package com.salesianostriana.dam.proyectobookversedanielmejias2.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.salesianostriana.dam.proyectobookversedanielmejias2.services.CarritoCompraService;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.PedidoService;

import lombok.RequiredArgsConstructor;

@Controller @RequiredArgsConstructor
@Scope (value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CarritoCompraController {

	private final CarritoCompraService carritoCompraService;
	private final PedidoService pedidoService;
	
	
	
	@GetMapping ("/carrito")
    public String showCarrito (Model model) {
    	model.addAttribute("lineas", carritoCompraService.getLineasCarrito());
    		
    	return "carrito";
    }
	
	
	
	@GetMapping("/productoACarrito/{isbn}")
	public String aniadirLibroAlCarrito(@PathVariable("isbn") String isbn) {
		carritoCompraService.addProducto(isbn);
		return "redirect:/carrito";
	}

	
	
	@PostMapping("/carrito/restar")
	public String restarLibroDelCarrito(@RequestParam("isbn") String isbn) {
		carritoCompraService.restarProducto(isbn);
		return "redirect:/carrito";
	}

	
	
	@PostMapping("/carrito/eliminar")
	public String eliminarLibroDelCarrito(@RequestParam("isbn") String isbn) {
		carritoCompraService.eliminarProducto(isbn);
		return "redirect:/carrito";
	}

	
	
	@PostMapping("/carrito/vaciar")
	public String vaciarCarrito() {
		carritoCompraService.vaciarCarrito();
		return "redirect:/carrito";
	}

	
	
	@PostMapping("/pedido/confirmar")
	public String confirmarPedido(Authentication authentication, RedirectAttributes redirectAttributes) {
		boolean pedidoCreado = authentication != null
				&& pedidoService.crearPedidoDesdeCarrito(authentication.getName(), carritoCompraService.getLineasCarrito());

		if (pedidoCreado) {
			carritoCompraService.vaciarCarrito();
			redirectAttributes.addFlashAttribute("mensajeExito", "Pedido creado correctamente.");
		} else {
			redirectAttributes.addFlashAttribute("mensajeError", "No se ha podido crear el pedido. Revisa el carrito y el stock.");
		}

		return "redirect:/carrito";
	}
	
	
	
}
