package com.salesianostriana.dam.proyectobookversedanielmejias2.exception;

public class LibroNoEliminableException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public LibroNoEliminableException(String isbn) {
		super("No se puede eliminar el libro con ISBN " + isbn + " porque aparece en uno o mas pedidos.");
	}

}
