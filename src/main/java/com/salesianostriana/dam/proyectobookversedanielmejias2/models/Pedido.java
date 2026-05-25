package com.salesianostriana.dam.proyectobookversedanielmejias2.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    private String metodoPago; 
    private String direccionEnvio;
    
    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Cliente cliente; //FK
    
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineaPedido> lineasPedido = new ArrayList<>(); 
	
}
