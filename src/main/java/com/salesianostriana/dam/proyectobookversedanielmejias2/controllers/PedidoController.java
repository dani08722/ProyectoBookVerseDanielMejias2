package com.salesianostriana.dam.proyectobookversedanielmejias2.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.salesianostriana.dam.proyectobookversedanielmejias2.models.EstadoPedido;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Pedido;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.ClienteService;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.LibroService;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.PedidoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PedidoController {

	private final PedidoService pedidoService;
	private final ClienteService clienteService;
	private final LibroService libroService;

	
	
	@GetMapping("/admin/pedidos")
	public String listarPedidos(Model model) {
		model.addAttribute("pedidos", pedidoService.findAll());
		return "admin/lista-pedidos";
	}
	
	
	
	@GetMapping("/admin/pedidos/{id}")
	public String verDetallePedido(@PathVariable Long id, Model model) {
		return pedidoService.findById(id)
				.map(pedido -> {
					model.addAttribute("pedido", pedido);
					return "admin/detalle-pedido";
				})
				.orElse("redirect:/admin/pedidos");
	}

	
	
	@GetMapping("/admin/pedidos/crear")
	public String mostrarFormularioCrear(Model model) {
		Pedido pedido = new Pedido();
		pedido.setFecha(LocalDate.now());
		pedido.setEstado(EstadoPedido.PENDIENTE);

		model.addAttribute("pedido", pedido);
		model.addAttribute("clientes", clienteService.findAll());
		model.addAttribute("libros", libroService.findAll());
		model.addAttribute("estados", EstadoPedido.values());
		return "admin/form-pedidos";
	}

	
	
	@GetMapping("/admin/pedidos/editar/{id}")
	public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
		return pedidoService.findById(id)
				.map(pedido -> {
					model.addAttribute("pedido", pedido);
					model.addAttribute("estados", EstadoPedido.values());
					return "admin/form-editar-pedidos";
				})
				.orElse("redirect:/admin/pedidos");
	}

	
	
	@PostMapping("/admin/pedidos/editar/submit")
	public String editarPedido(@ModelAttribute("pedido") Pedido pedido) {
		pedidoService.modificarPedido(pedido);
		return "redirect:/admin/pedidos";
	}

	
	@PostMapping("/admin/pedidos/eliminar/{id}")
	public String eliminarPedido(@PathVariable Long id) {
		pedidoService.eliminarPedido(id);
		return "redirect:/admin/pedidos";
	}

	
	
	@PostMapping("/admin/pedidos/submit")
	public String crearPedido(@ModelAttribute("pedido") Pedido pedido,
			@RequestParam("clienteId") Long clienteId,
			@RequestParam("isbns") List<String> isbns,
			@RequestParam("cantidades") List<Integer> cantidades,
			RedirectAttributes redirectAttributes) {

		if (!pedidoService.crearPedido(pedido, clienteId, isbns, cantidades)) {
			redirectAttributes.addFlashAttribute("mensajeError", "Debes seleccionar libros con stock suficiente.");
			return "redirect:/admin/pedidos/crear";
		}

		return "redirect:/admin/pedidos";
	}

}
