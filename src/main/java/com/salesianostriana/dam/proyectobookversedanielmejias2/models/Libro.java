package com.salesianostriana.dam.proyectobookversedanielmejias2.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Libro {

    @Id
    private String isbn;

    private String titulo;
    private String autor;
    private String editorial;
    private int anioPublicacion;
    private String genero;
    private String idioma;
    private int numeroPaginas;

    private double precio;
    private int stock;

    private String descripcion;
    private String imagenUrl;
    private boolean disponible;
    
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "libro", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<LineaPedido> lineasPedido = new ArrayList<>();

}
