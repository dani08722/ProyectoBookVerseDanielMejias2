package com.salesianostriana.dam.proyectobookversedanielmejias2.exception;

public class LibroNoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LibroNoEncontradoException(String isbn) {
		super("Libro no encontrado con el ISBN: " + isbn);
	}
}
