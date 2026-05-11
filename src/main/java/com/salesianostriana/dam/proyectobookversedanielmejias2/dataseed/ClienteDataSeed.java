package com.salesianostriana.dam.proyectobookversedanielmejias2.dataseed;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.salesianostriana.dam.proyectobookversedanielmejias2.repository.ClienteRepository;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Cliente;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClienteDataSeed {

	private final ClienteRepository repo;
	
	@PostConstruct
	public void iniciarCliente() {
		
		if (repo.count() == 0) {
            
            // Primer cliente
            repo.save(Cliente.builder()
                    .nombre("Daniel")
                    .apellidos("Mejías")
                    .email("daniel.mejias@bookverse.com")
                    .telefono("600123456")
                    .direccion("Calle de la Lectura, 42")
                    .ciudad("Sevilla")
                    .codigoPostal("41010")
                    .pais("España")
                    .password("12345") 
                    .fechaRegistro(LocalDate.now())
                    .activo(true)
                    .build());

            // Segundo cliente
            repo.save(Cliente.builder()
                    .nombre("Elena")
                    .apellidos("García")
                    .email("elena.garcia@bookverse.com")
                    .telefono("611987654")
                    .direccion("Avenida del Libro, 15")
                    .ciudad("Madrid")
                    .codigoPostal("28001")
                    .pais("España")
                    .password("qwerty")
                    .fechaRegistro(LocalDate.now().minusDays(5))
                    .activo(true)
                    .build());
        }

	}
	
}
