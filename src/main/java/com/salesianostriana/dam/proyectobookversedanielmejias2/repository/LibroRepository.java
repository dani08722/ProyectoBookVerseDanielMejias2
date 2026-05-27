package com.salesianostriana.dam.proyectobookversedanielmejias2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Libro;

public interface LibroRepository extends JpaRepository<Libro, String>{

	@Query("select l.isbn from Libro l")
	List<String> obtenerIds();

}
