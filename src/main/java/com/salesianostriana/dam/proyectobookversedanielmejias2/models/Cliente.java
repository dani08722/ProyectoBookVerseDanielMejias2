package com.salesianostriana.dam.proyectobookversedanielmejias2.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity 
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Cliente {

	@Id
	@GeneratedValue
	private Long idCliente; // PK
	
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;

    private String direccion;
    private String ciudad;
    private String codigoPostal;
    private String pais;
    
    private LocalDate fechaRegistro;
    private boolean activo;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", unique = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
    
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();
	
}
