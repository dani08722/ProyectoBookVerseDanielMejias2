package com.salesianostriana.dam.proyectobookversedanielmejias2.services;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.salesianostriana.dam.proyectobookversedanielmejias2.exception.StockInsuficienteException;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.LineaPedido;
import com.salesianostriana.dam.proyectobookversedanielmejias2.repository.LibroRepository;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.base.BaseServiceImpl;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Libro;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CarritoCompraService extends BaseServiceImpl<Libro, String, LibroRepository>{

	private List<LineaPedido> lineas = new ArrayList<>();
	
	public void addProducto (Libro l) {
		Optional<LineaPedido> lineaExistente = buscarLineaPorLibro(l);
		int cantidadActual = lineaExistente
				.map(LineaPedido::getCantidad)
				.orElse(0);
		
		if (cantidadActual >= l.getStock()) {
			throw new StockInsuficienteException(l.getTitulo(), l.getStock());
		}
		
		if (lineaExistente.isPresent()) {
			LineaPedido linea = lineaExistente.get();
			linea.setCantidad(linea.getCantidad() + 1);
		} else {
			lineas.add(LineaPedido.builder()
					.libro(l)
					.cantidad(1)
					.precioUnitario(l.getPrecio())
					.build());
		}
	}

	public void restarProducto(String isbn) {
		buscarLineaPorIsbn(isbn)
				.ifPresent(linea -> {
					if (linea.getCantidad() > 1) {
						linea.setCantidad(linea.getCantidad() - 1);
					} else {
						lineas.remove(linea);
					}
				});
	}

	public void eliminarProducto(String isbn) {
		lineas.removeIf(linea -> linea.getLibro().getIsbn().equals(isbn));
	}

	public void vaciarCarrito() {
		lineas.clear();
	}
	
	private Optional<LineaPedido> buscarLineaPorLibro(Libro libro) {
		return buscarLineaPorIsbn(libro.getIsbn());
	}

	private Optional<LineaPedido> buscarLineaPorIsbn(String isbn) {
		return lineas.stream()
				.filter(linea -> linea.getLibro().getIsbn().equals(isbn))
				.findFirst();
	}
	
	public List<LineaPedido> getLineasCarrito() {
        return Collections.unmodifiableList(lineas);
    }
	
	public long cantidadTotal() {
		return lineas.stream()
				.mapToLong(LineaPedido::getCantidad)
				.sum();
	}
	
	public double total() {
		return lineas.stream()
				.mapToDouble(LineaPedido::getSubtotal)
				.sum();
	}
	
	
}
