package com.salesianostriana.dam.proyectobookversedanielmejias2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Cliente;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.ClienteService;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.LibroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private static final int NUM_PRODUCTOS_ALEATORIOS = 4;

	private final ClienteService clienteService;
	private final LibroService libroService;

	
	
	@GetMapping({"/", "/index"})
	public String index(Model model) {
		model.addAttribute("Clientes", clienteService.findAll());
		model.addAttribute("librosDestacados", libroService.obtenerLibrosAleatorios(NUM_PRODUCTOS_ALEATORIOS));
		return "index";
	}

	
	
	@GetMapping("/acceso-denegado")
	public String accesoDenegado() {
		return "acceso-denegado";
	}

	
	
	@GetMapping("/registro")
	public String mostrarRegistro(Model model) {
		model.addAttribute("cliente", clienteService.nuevoCliente());
		return "registro";
	}
	
	
	
	@PostMapping("/registro")
	public String registrarCliente(@Valid @ModelAttribute("cliente") Cliente cliente,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes,
			Model model) {

		if (cliente.getUser() == null || cliente.getUser().getPassword() == null
				|| cliente.getUser().getPassword().isBlank()) {
			bindingResult.rejectValue("user.password", "NotBlank.cliente.user.password",
					"La contraseña es obligatoria.");
		}

		if (bindingResult.hasErrors()) {
			return "registro";
		}

		if (clienteService.existeUsername(cliente.getUser().getUsername())) {
			bindingResult.rejectValue("user.username", "Duplicado.cliente.user.username",
					"El nombre de usuario ya está registrado.");
			return "registro";
		}

		if (clienteService.existeEmail(cliente.getEmail())) {
			bindingResult.rejectValue("email", "Duplicado.cliente.email",
					"El email ya está registrado.");
			return "registro";
		}

		clienteService.registrarCliente(cliente);
		redirectAttributes.addFlashAttribute("mensajeExito", "Cuenta creada correctamente. Ya puedes iniciar sesion.");
		return "redirect:/login";
	}

}
