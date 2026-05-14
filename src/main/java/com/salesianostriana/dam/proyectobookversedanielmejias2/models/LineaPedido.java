package com.salesianostriana.dam.proyectobookversedanielmejias2.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class LineaPedido {

	@Id
	@GeneratedValue
	private Long idLineaPedido; //PK
	
	@ManyToOne
	@JoinColumn(name = "pedido_id")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Pedido pedido; //FK
	
	@ManyToOne
    @JoinColumn(name = "libro_isbn")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Libro libro; //FK
	
	private int cantidad;
	private double precioUnitario;
	
	@Transient
	public double getSubtotal() {
		return cantidad * precioUnitario;
	}
	
}
