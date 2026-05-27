package com.salesianostriana.dam.proyectobookversedanielmejias2.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Libro;
import com.salesianostriana.dam.proyectobookversedanielmejias2.repository.LibroRepository;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.base.BaseServiceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LibroService extends BaseServiceImpl<Libro, String, LibroRepository>{

	private final LineaPedidoService lineaPedidoService;

	
	
	public List<Libro> obtenerLibrosAleatorios(int numero) {
		List<String> listaIds = repository.obtenerIds();
		Collections.shuffle(listaIds);
		listaIds = listaIds.stream().limit(numero).collect(Collectors.toList());
		return repository.findAllById(listaIds);
	}

	
	@Transactional
	public void eliminarLibro(String isbn) {
		findById(isbn)
				.ifPresent(libro -> {
					lineaPedidoService.deleteByIsbn(isbn);
					delete(libro);
				});
	}

}
