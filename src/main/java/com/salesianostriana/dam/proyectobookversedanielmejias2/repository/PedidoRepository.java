package com.salesianostriana.dam.proyectobookversedanielmejias2.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.proyectobookversedanielmejias2.models.EstadoPedido;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

	boolean existsByClienteIdClienteAndEstadoIn(Long idCliente, Collection<EstadoPedido> estados);

}
