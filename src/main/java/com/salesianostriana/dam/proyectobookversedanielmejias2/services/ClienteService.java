package com.salesianostriana.dam.proyectobookversedanielmejias2.services;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Cliente;

@Service
public class ClienteService {

	public List<Cliente> listaClientes(){
		return Arrays.asList(
				Cliente.builder()
						.idCliente(2L)
						.nombre("Daniel")
						.apellidos("Mejias Lora")
						.email("mejiaslora@gmail.com")
						.telefono("+644 44 44 44")
						.direccion("Calle Perico")
						.ciudad("Sevilla")
						.codigoPostal("41010")
						.pais("Espana")
						.password("1234")
						.fechaRegistro(LocalDate.now())
						.activo(true)
						.build()
				);
	}
	
}
