package com.salesianostriana.dam.proyectobookversedanielmejias2.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Cliente;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.User;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.UserRole;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.base.BaseServiceImpl;
import com.salesianostriana.dam.proyectobookversedanielmejias2.repository.ClienteRepository;
import com.salesianostriana.dam.proyectobookversedanielmejias2.repository.PedidoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService extends BaseServiceImpl<Cliente, Long, ClienteRepository> {

	private final PasswordEncoder passwordEncoder;
	private final PedidoRepository pedidoRepository;

	
	
	public Cliente nuevoCliente() {
		Cliente cliente = new Cliente();
		cliente.setActivo(true);
		cliente.setUser(User.builder()
				.role(UserRole.USER)
				.build());
		return cliente;
	}

	
	
	public Cliente prepararClienteParaFormulario(Cliente cliente) {
		if (cliente.getUser() == null) {
			cliente.setUser(User.builder()
					.role(UserRole.USER)
					.build());
		}

		return cliente;
	}

	
	
	public Optional<Cliente> buscarPorUsername(String username) {
		return repository.findByUserUsername(username);
	}

	
	
	public Cliente guardarCliente(Cliente cliente) {
		if (cliente.getIdCliente() == null) {
			cliente.setFechaRegistro(LocalDate.now());
			cliente.setActivo(true);
		} else {
			findById(cliente.getIdCliente())
					.ifPresent(clienteExistente -> mantenerDatosNoEditables(cliente, clienteExistente));
		}

		prepararUsuario(cliente);
		return save(cliente);
	}

	
	
	@Transactional
	public boolean eliminarCliente(Long id) {
		return findById(id)
				.map(cliente -> {
					if (cliente.getUser().getRole() == UserRole.ADMIN) {
						return false;
					}

					new ArrayList<>(cliente.getPedidos()).forEach(pedidoRepository::delete);
					delete(cliente);
					return true;
				})
				.orElse(false);
	}

	
	
	private void mantenerDatosNoEditables(Cliente cliente, Cliente clienteExistente) {
		//Mantenemos la fecha de registro y el estado activo originales del cliente
		cliente.setFechaRegistro(clienteExistente.getFechaRegistro());
		cliente.setActivo(clienteExistente.isActivo());

		//Si el usuario existe tanto en el objeto nuevo como en el de la base de datos
		if (clienteExistente.getUser() != null && cliente.getUser() != null) {			
			//Mantenemos el mismo rol que ya tenía asignado
			cliente.getUser().setRole(clienteExistente.getUser().getRole());
			
			//Si la nueva contraseña viene vacía o nula, recuperamos la contraseña antigua
			if (cliente.getUser().getPassword() == null || cliente.getUser().getPassword().isBlank()) {
				cliente.getUser().setPassword(clienteExistente.getUser().getPassword());
			}
		}
	}

	
	
	private void prepararUsuario(Cliente cliente) {
		User user = cliente.getUser();
		String password;
		
		//Si el cliente no tiene un usuario asociado, se crea una nueva instancia de User y se vincula al cliente.
		if (user == null) {
			user = new User();
			cliente.setUser(user);
		}

		if (user.getRole() == null) {
			user.setRole(UserRole.USER);
		}

		//Setea el cliente en user para asegurar que estan relacionados
		user.setCliente(cliente);

		password= user.getPassword();
		//Primero se asegura de que haya contraseña y que la contraseña no esté ya encriptada
		//para luego encriptarla

		if (password != null && !password.isBlank() && !password.startsWith("{")) {
			user.setPassword(passwordEncoder.encode(password));
		}
	}
	
}
