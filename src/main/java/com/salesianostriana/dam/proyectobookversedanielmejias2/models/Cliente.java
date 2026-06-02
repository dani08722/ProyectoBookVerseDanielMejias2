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
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class Cliente {

	@Id
	@GeneratedValue
	private Long idCliente; // PK
	
    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 80, message = "El nombre no puede superar los 80 caracteres.")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios.")
    @Size(max = 120, message = "Los apellidos no pueden superar los 120 caracteres.")
    private String apellidos;

    @NotBlank(message = "El email es obligatorio.")
    @Email(message = "El email debe tener un formato válido.")
    @Size(max = 150, message = "El email no puede superar los 150 caracteres.")
    private String email;

    @Pattern(regexp = "^$|^[0-9+()\\s-]{7,20}$", message = "El teléfono debe tener un formato válido.")
    private String telefono;

    @Size(max = 200, message = "La dirección no puede superar los 200 caracteres.")
    private String direccion;

    @Size(max = 100, message = "La ciudad no puede superar los 100 caracteres.")
    private String ciudad;

    @Pattern(regexp = "^$|^[0-9]{5}$", message = "El código postal debe tener 5 dígitos.")
    private String codigoPostal;

    @Size(max = 80, message = "El país no puede superar los 80 caracteres.")
    private String pais;
    
    private LocalDate fechaRegistro;
    private boolean activo;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", unique = true)
    @Valid
    @NotNull(message = "Los datos de usuario son obligatorios.")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
    
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();
	
}
