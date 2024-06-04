package com.iesfernandowirtz.ClothesVault.modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "detalle_pedido")
public class modeloDetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private modeloPedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private modeloProducto producto;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_total")
    private Double precioTotal;

    // Constructor y getters/setters
}
