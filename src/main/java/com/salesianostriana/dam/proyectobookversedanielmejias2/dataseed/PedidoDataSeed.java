package com.salesianostriana.dam.proyectobookversedanielmejias2.dataseed;

import java.time.LocalDate;
import org.springframework.stereotype.Component;

import com.salesianostriana.dam.proyectobookversedanielmejias2.repository.PedidoRepository;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Cliente;
import com.salesianostriana.dam.proyectobookversedanielmejias2.models.Pedido;
import com.salesianostriana.dam.proyectobookversedanielmejias2.repository.ClienteRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PedidoDataSeed {

    private final PedidoRepository pedidoRepo;
    private final ClienteRepository clienteRepo;
    
    @PostConstruct
    public void iniciar() {
        if (pedidoRepo.count() == 0) {
            Cliente c = clienteRepo.findAll().stream().findFirst().orElse(null);

            if (c != null) {
                pedidoRepo.save(Pedido.builder()
                        .codigo("PED-2024-001")
                        .fecha(LocalDate.now())
                        .cliente(c)
                        .estado("ENTREGADO")
                        .metodoPago("TARJETA")
                        .direccionEnvio(c.getDireccion())
                        .total(34.50) 
                        .build());
                
            }
        }
    }
}