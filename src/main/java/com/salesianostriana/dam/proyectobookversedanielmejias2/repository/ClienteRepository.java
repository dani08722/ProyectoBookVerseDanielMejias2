package com.salesianostriana.dam.proyectobookversedanielmejias2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	Optional<Cliente> findByUserUsername(String username);

}
