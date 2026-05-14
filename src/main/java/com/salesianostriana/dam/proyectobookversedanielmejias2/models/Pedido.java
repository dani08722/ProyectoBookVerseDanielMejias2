package com.salesianostriana.dam.proyectobookversedanielmejias2.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Pedido {

	@Id 
	@GeneratedValue
	private Long idPedido; // PK
	
    private String codigo;
    private LocalDate fecha;

    private double total;

    private String estado; // pendiente, enviado, entregado
    private String metodoPago; 
    private String direccionEnvio;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente; //FK
    
    @Builder.Default
    @OneToMany(mappedBy = "pedido")
    private List<LineaPedido> lineasPedido = new ArrayList<>(); 
	
}
