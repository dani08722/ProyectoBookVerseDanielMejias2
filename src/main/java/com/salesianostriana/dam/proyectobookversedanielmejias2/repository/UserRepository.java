package com.salesianostriana.dam.proyectobookversedanielmejias2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.proyectobookversedanielmejias2.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findFirstByUsername(String username);
	
	boolean existsByUsername(String username);

}
