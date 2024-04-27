package com.iesfernandowirtz.ClothesVault.modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "detalle_pedido")
public class modeloDetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detalle_pedidos_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private modeloPedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private modeloProducto producto;

    @Column(name = "PrecioTotal")
    private Integer precioTotal;

    @Column(name = "Fecha")
    private Date fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Integer precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}