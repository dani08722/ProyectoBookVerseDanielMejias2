package com.salesianostriana.dam.proyectobookversedanielmejias2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salesianostriana.dam.proyectobookversedanielmejias2.models.LineaPedido;

public interface LineaPedidoRepository extends JpaRepository<LineaPedido, Long> {

	@Modifying
	@Query("delete from LineaPedido lp where lp.libro.isbn = :isbn")
	void deleteByIsbn(@Param("isbn") String isbn);

}
