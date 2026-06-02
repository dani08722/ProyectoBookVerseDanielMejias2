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
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @DecimalMin(value = "0.0", message = "El total no puede ser negativo.")
    private double total;

    @NotBlank(message = "El método de pago es obligatorio.")
    private String metodoPago; 

    @NotBlank(message = "La dirección de envío es obligatoria.")
    @Size(max = 250, message = "La dirección de envío no puede superar los 250 caracteres.")
    private String direccionEnvio;
    
    @NotNull(message = "El estado es obligatorio.")
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
