package com.salesianostriana.dam.proyectobookversedanielmejias2.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.proyectobookversedanielmejias2.repository.LibroRepository;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.base.BaseServiceImpl;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Libro;

@Service
public class CarritoCompraService extends BaseServiceImpl<Libro, String, LibroRepository>{

	private Map<Libro, Long> libros = new HashMap <> ();
	
	public Map<Libro, Long> getProductosCarrito() {
        return Collections.unmodifiableMap(libros);
    }
	
}
