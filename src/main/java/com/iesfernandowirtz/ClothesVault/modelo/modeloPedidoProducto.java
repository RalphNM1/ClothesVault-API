package com.iesfernandowirtz.ClothesVault.modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@Table(name = "pedido_producto")
public class modeloPedidoProducto {

    @Id
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private modeloPedido pedido;

    @Id
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private modeloProducto producto;

    private Integer cantidad;

    public modeloPedido getPedido() {
        return pedido;
    }

    public void setPedido(modeloPedido pedido) {
        this.pedido = pedido;
    }

    public modeloProducto getProducto() {
        return producto;
    }

    public void setProducto(modeloProducto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}