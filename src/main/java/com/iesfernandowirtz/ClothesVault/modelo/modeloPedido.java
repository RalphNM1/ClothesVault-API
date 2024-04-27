package com.iesfernandowirtz.ClothesVault.modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "pedido")
public class modeloPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private modeloUsuario usuario;

    @Column(name = "direccion_entrega")
    private String direccionEntrega;

    private String estado;

    @Column(name = "fecha_pedido")
    private Date fechaPedido;

    @OneToMany(mappedBy = "pedido")
    private Set<modeloDetallePedido> detallePedidos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public modeloUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(modeloUsuario usuario) {
        this.usuario = usuario;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Set<modeloDetallePedido> getDetallePedidos() {
        return detallePedidos;
    }

    public void setDetallePedidos(Set<modeloDetallePedido> detallePedidos) {
        this.detallePedidos = detallePedidos;
    }
}