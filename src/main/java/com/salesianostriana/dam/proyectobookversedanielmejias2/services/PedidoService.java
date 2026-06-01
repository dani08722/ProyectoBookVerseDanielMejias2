package com.salesianostriana.dam.proyectobookversedanielmejias2.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Cliente;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Libro;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.LineaPedido;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Pedido;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.EstadoPedido;
import com.salesianostriana.dam.proyectobookversedanielmejias2.repository.PedidoRepository;
import com.salesianostriana.dam.proyectobookversedanielmejias2.services.base.BaseServiceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService extends BaseServiceImpl<Pedido, Long, PedidoRepository>{

	private final ClienteService clienteService;
	private final LibroService libroService;

	
	
	
	public Pedido nuevoPedido() {
		Pedido pedido = new Pedido();
		pedido.setFecha(LocalDate.now());
		pedido.setEstado(EstadoPedido.PENDIENTE);
		return pedido;
	}

	public EstadoPedido[] obtenerEstados() {
		return EstadoPedido.values();
	}
	
	
	public void modificarPedido(Pedido pedidoEditado) {
		findById(pedidoEditado.getIdPedido())
				.ifPresent(pedido -> {
					pedido.setDireccionEnvio(pedidoEditado.getDireccionEnvio());
					pedido.setEstado(pedidoEditado.getEstado());
					pedido.setMetodoPago(pedidoEditado.getMetodoPago());
					save(pedido);
				});
	}
	
	
	@Transactional
	public void eliminarPedido(Long id) {
		findById(id).ifPresent(this::delete);
	}
	
	
	
	@Transactional
	public boolean crearPedido(Pedido pedido, Long clienteId, List<String> isbns, List<Integer> cantidades) {
		List<LineaPedido> lineasPedido = crearLineasPedido(pedido, isbns, cantidades);

		//Si no hay lineas de pedido o no hay stock en una linea devuelve false
		if (lineasPedido.isEmpty() || !hayStockSuficiente(lineasPedido)) {
			return false;
		}

		//Si no se pone el codigo a mano se pone uno aleatorio
		if (pedido.getCodigo() == null || pedido.getCodigo().isBlank()) {
			pedido.setCodigo("PED-" + System.currentTimeMillis());
		}
		
		//Seteamos la fecha del pedido a la fecha de hoy por si acaso no funciona de la otra forma
		pedido.setFecha(LocalDate.now());
		
		//Calcular el total
		clienteService.findById(clienteId).ifPresent(pedido::setCliente);
		pedido.setLineasPedido(lineasPedido);
		pedido.setTotal(lineasPedido.stream()
				.mapToDouble(LineaPedido::getSubtotal)
				.sum());

		//Descontar del stock los libros pedidos
		descontarStock(lineasPedido);
		
		save(pedido);
		return true;
	}
	
	

	@Transactional
	public boolean crearPedidoDesdeCarrito(String username, List<LineaPedido> lineasCarrito) {
		
		//Si el carrito es nulo o está vacío no se puede procesar el pedido y devuelve false
		if (lineasCarrito == null || lineasCarrito.isEmpty()) {
			return false;
		}
		
		
		
		//Buscamos al cliente por su username para procesar la compra y lo devolvemos
		return clienteService.buscarPorUsername(username)
				.map(cliente -> {
					//Inicializamos un nuevo pedido con datos por defecto
					Pedido pedido = nuevoPedido();
					pedido.setMetodoPago("Pendiente");
					pedido.setDireccionEnvio(crearDireccionEnvio(cliente));
					
					//Extraemos todos los ISBNs de los libros que están en el carrito
					List<String> isbns = lineasCarrito.stream()
							.map(linea -> linea.getLibro().getIsbn())
							.collect(Collectors.toList());
					
					//Extraemos las cantidades de cada libro del carrito
					List<Integer> cantidades = lineasCarrito.stream()
							.map(LineaPedido::getCantidad)
							.collect(Collectors.toList());
					
					//Llamamos al método principal para crear y validar el pedido final
					return crearPedido(pedido, cliente.getIdCliente(), isbns, cantidades);
				})
				//Si no hay cliente directamente devuelve false
				.orElse(false);
	}
	
	

	private String crearDireccionEnvio(Cliente cliente) {
		//Creo una lista para poner las direcciones
		List<String> partesDireccion = new ArrayList<>();
			
			//Añado a la lista la direccion
			partesDireccion.add(cliente.getDireccion());
			//Añado a la lista la ciudad
			partesDireccion.add(cliente.getCiudad());
		
		//Unimos las partes de la dirección
		return String.join(", ", partesDireccion);
	}

	
	
	private List<LineaPedido> crearLineasPedido(Pedido pedido, List<String> isbns, List<Integer> cantidades) {
		//Inicializamos la lista de lineas de pedido vacia
		List<LineaPedido> lineasPedido = new ArrayList<>();

		//Recorremos ambas listas a la vez controlando para no salirnos de los límites
		for (int i = 0; i < isbns.size() && i < cantidades.size(); i++) {
			String isbn = isbns.get(i);
			int cantidad = cantidades.get(i);
			
			//Si el ISBN es nulo, está vacio o la cantidad es menor o igual a cero pasamos al siguiente
			if (isbn == null || isbn.isBlank() || cantidad <= 0) {
				continue;
			}			
			
			//Buscamos el libro y si existe añadimos la línea de pedido con su precio actual
			libroService.findById(isbn)
					.ifPresent(libro -> lineasPedido.add(LineaPedido.builder()
							.pedido(pedido)
							.libro(libro)
							.cantidad(cantidad)
							.precioUnitario(libro.getPrecioConDescuento())
							.build()));
		}
		//Devolvemos la lista con todas las líneas generadas
		return lineasPedido;
	}

	
	
	//Mira todas las lineas de pedido para ver si la cantidad del pedido es mayor del stock
	private boolean hayStockSuficiente(List<LineaPedido> lineasPedido) {
		for (LineaPedido linea : lineasPedido) {
			if (linea.getCantidad() > linea.getLibro().getStock()) {
				return false;
			}
		}

		return true;
	}

	
	
	//Resta al stock de la tienda la cantidad de libros que hemos comprado
	private void descontarStock(List<LineaPedido> lineasPedido) {
		for (LineaPedido linea : lineasPedido) {
			Libro libro = linea.getLibro();
			libro.setStock(libro.getStock() - linea.getCantidad());
			libro.setDisponible(libro.getStock() > 0);
			libroService.save(libro);
		}
	}

}
