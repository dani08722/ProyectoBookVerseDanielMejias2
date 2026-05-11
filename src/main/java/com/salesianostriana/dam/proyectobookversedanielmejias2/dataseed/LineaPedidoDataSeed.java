package com.salesianostriana.dam.proyectobookversedanielmejias2.dataseed;

import org.springframework.stereotype.Component;

import com.salesianostriana.dam.proyectobookversedanielmejias2.repository.LineaPedidoRepository;
import com.salesianostriana.dam.proyectobookversedanielmejias2.repository.PedidoRepository;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Libro;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.LineaPedido;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Pedido;
import com.salesianostriana.dam.proyectobookversedanielmejias2.repository.LibroRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LineaPedidoDataSeed {

    private final LineaPedidoRepository lineaRepo;
    private final PedidoRepository pedidoRepo;
    private final LibroRepository libroRepo;
    
    @PostConstruct
    public void iniciar() {
        if (lineaRepo.count() == 0) {
            Pedido pedido = pedidoRepo.findAll().stream().findFirst().orElse(null);
            Libro quijote = libroRepo.findById("978-84-376-0494-7").orElse(null);
            Libro harryPotter = libroRepo.findById("978-84-9838-708-7").orElse(null);

            if (pedido != null && quijote != null && harryPotter != null) {
                
                // Línea para El Quijote
                lineaRepo.save(LineaPedido.builder()
                        .pedido(pedido)
                        .libro(quijote)
                        .cantidad(1)
                        .precioUnitario(quijote.getPrecio())
                        .build());

                // Línea para Harry Potter
                lineaRepo.save(LineaPedido.builder()
                        .pedido(pedido)
                        .libro(harryPotter)
                        .cantidad(1)
                        .precioUnitario(harryPotter.getPrecio())
                        .build());

            }
        }
    }
}