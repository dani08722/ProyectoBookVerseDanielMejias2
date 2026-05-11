package com.salesianostriana.dam.proyectobookversedanielmejias2.dataseed;

import org.springframework.stereotype.Component;

import com.salesianostriana.dam.proyectobookversedanielmejias2.repository.LibroRepository;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Libro;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LibroDataSeed {

    private final LibroRepository repo;
    
    @PostConstruct
    public void iniciar() {
        
        if (repo.count() == 0) {
            
            // Primer libro
            repo.save(Libro.builder()
                    .isbn("978-84-376-0494-7")
                    .titulo("El Quijote")
                    .autor("Miguel de Cervantes")
                    .editorial("Cátedra")
                    .anioPublicacion(1605)
                    .genero("Clásico")
                    .idioma("Español")
                    .numeroPaginas(1376)
                    .precio(15.50)
                    .stock(10)
                    .descripcion("La historia del ingenioso hidalgo Don Quijote de la Mancha.")
                    .imagenUrl("https://via.placeholder.com/300x450?text=El+Quijote")
                    .disponible(true)
                    .build());

            // Segundo libro
            repo.save(Libro.builder()
                    .isbn("978-84-9838-708-7")
                    .titulo("Harry Potter y la Piedra Filosofal")
                    .autor("J.K. Rowling")
                    .editorial("Salamandra")
                    .anioPublicacion(1997)
                    .genero("Fantasía")
                    .idioma("Español")
                    .numeroPaginas(256)
                    .precio(19.00)
                    .stock(50)
                    .descripcion("El inicio de la apasionante saga del joven mago.")
                    .imagenUrl("https://via.placeholder.com/300x450?text=Harry+Potter")
                    .disponible(true)
                    .build());

            // Tercer libro
            repo.save(Libro.builder()
                    .isbn("978-84-666-5973-4")
                    .titulo("Dune")
                    .autor("Frank Herbert")
                    .editorial("Nova")
                    .anioPublicacion(1965)
                    .genero("Ciencia Ficción")
                    .idioma("Español")
                    .numeroPaginas(704)
                    .precio(21.90)
                    .stock(25)
                    .descripcion("Arrakis, el planeta del desierto, el único lugar donde se encuentra la especia melange...")
                    .imagenUrl("https://via.placeholder.com/300x450?text=Dune")
                    .disponible(true)
                    .build());
            
        }
    }
}