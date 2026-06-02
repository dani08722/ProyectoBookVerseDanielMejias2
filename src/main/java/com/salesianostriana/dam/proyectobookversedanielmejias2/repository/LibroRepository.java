package com.salesianostriana.dam.proyectobookversedanielmejias2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Libro;

public interface LibroRepository extends JpaRepository<Libro, String>{

    @Query("select l.isbn from Libro l")
    List<String> obtenerIds();

    
    
    @Query("""
            SELECT l
            FROM Libro l
            WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :texto, '%'))
               OR LOWER(l.autor) LIKE LOWER(CONCAT('%', :texto, '%'))
               OR LOWER(l.isbn) LIKE LOWER(CONCAT('%', :texto, '%'))
               OR LOWER(l.genero) LIKE LOWER(CONCAT('%', :texto, '%'))
            """)
    List<Libro> buscarPorTexto(String texto);

}