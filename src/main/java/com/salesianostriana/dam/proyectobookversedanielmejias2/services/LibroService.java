package com.salesianostriana.dam.proyectobookversedanielmejias2.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.proyectobookversedanielmejias2.exception.LibroDuplicadoException;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Libro;
import com.salesianostriana.dam.proyectobookversedanielmejias2.repository.LibroRepository;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.base.BaseServiceImpl;

import jakarta.transaction.Transactional;

@Service
public class LibroService extends BaseServiceImpl<Libro, String, LibroRepository>{

    public List<Libro> obtenerLibrosAleatorios(int numero) {
        List<String> listaIds = repository.obtenerIds();
        Collections.shuffle(listaIds);
        listaIds = listaIds.stream().limit(numero).collect(Collectors.toList());
        return repository.findAllById(listaIds);
    }
    
    

    public List<Libro> buscarPorTexto(String texto) {

        if (texto == null || texto.isBlank()) {
            return repository.findAll();
        }

        return repository.buscarPorTexto(texto);
    }
    
    

    public List<Libro> filtrarCatalogo(String genero, String texto) {

        if (texto != null && !texto.isBlank()) {
            return repository.buscarPorTexto(texto);
        }

        return repository.findAll();
    }
    
    

    public Libro crearLibro(Libro libro) {
        if (repository.existsById(libro.getIsbn())) {
            throw new LibroDuplicadoException(libro.getIsbn());
        }

        return save(libro);
    }
    
    

    @Transactional
    public void eliminarLibro(String isbn) {
        findById(isbn).ifPresent(this::delete);
    }

}
