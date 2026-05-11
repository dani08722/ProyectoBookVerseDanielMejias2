package com.salesianostriana.dam.proyectobookversedanielmejias2.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @OneToMany(mappedBy = "libro")
    private List<LineaPedido> lineasPedido = new ArrayList<>();

}
