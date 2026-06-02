package com.salesianostriana.dam.proyectobookversedanielmejias2.exception;

public class LibroDuplicadoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public LibroDuplicadoException(String isbn) {
		super("Ya existe un libro con el ISBN: " + isbn);
	}

}
