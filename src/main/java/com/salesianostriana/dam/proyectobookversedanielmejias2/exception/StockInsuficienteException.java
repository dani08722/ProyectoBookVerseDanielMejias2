package com.salesianostriana.dam.proyectobookversedanielmejias2.exception;

public class StockInsuficienteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StockInsuficienteException(String titulo, int stock) {
		super("No puedes anadir mas unidades de \"" + titulo + "\". Stock disponible: " + stock);
	}
}
