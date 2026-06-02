package com.salesianostriana.dam.proyectobookversedanielmejias2.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class Libro {

    @Id
    @NotBlank(message = "El ISBN es obligatorio.")
    @Size(max = 30, message = "El ISBN no puede superar los 30 caracteres.")
    private String isbn;

    @NotBlank(message = "El título es obligatorio.")
    @Size(max = 150, message = "El título no puede superar los 150 caracteres.")
    private String titulo;

    @NotBlank(message = "El autor es obligatorio.")
    @Size(max = 120, message = "El autor no puede superar los 120 caracteres.")
    private String autor;

    @Size(max = 120, message = "La editorial no puede superar los 120 caracteres.")
    private String editorial;

    @Min(value = 1, message = "El año de publicación debe ser mayor que 0.")
    private int anioPublicacion;

    @Size(max = 80, message = "El género no puede superar los 80 caracteres.")
    private String genero;

    @Size(max = 60, message = "El idioma no puede superar los 60 caracteres.")
    private String idioma;

    @Min(value = 1, message = "El número de páginas debe ser mayor que 0.")
    private int numeroPaginas;

    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0.")
    private double precio;

    @Min(value = 0, message = "El descuento no puede ser negativo.")
    @Max(value = 21, message = "El descuento no puede superar el 21%.")
    private int descuento;

    @Min(value = 0, message = "El stock no puede ser negativo.")
    private int stock;

    @Size(max = 2000, message = "La descripción no puede superar los 2000 caracteres.")
    private String descripcion;

    @Size(max = 500, message = "La URL de imagen no puede superar los 500 caracteres.")
    private String imagenUrl;
    private boolean disponible;
    
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "libro", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<LineaPedido> lineasPedido = new ArrayList<>();

    public boolean tieneDescuento() {
        return descuento > 0;
    }

    public double getPrecioConDescuento() {
        if (!tieneDescuento()) {
            return precio;
        }

        return precio * (100 - descuento) / 100.0;
    }

}
