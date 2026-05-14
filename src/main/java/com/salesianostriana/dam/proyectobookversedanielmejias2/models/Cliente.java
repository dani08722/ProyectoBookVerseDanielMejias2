package com.salesianostriana.dam.proyectobookversedanielmejias2.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    
    private String password;
    private LocalDate fechaRegistro;
    private boolean activo;
    
    @Builder.Default
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();
	
}
