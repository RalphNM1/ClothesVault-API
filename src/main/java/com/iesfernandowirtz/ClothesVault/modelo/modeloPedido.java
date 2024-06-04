package com.iesfernandowirtz.ClothesVault.modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;

@Setter
@Getter
@Entity
@Data
@NoArgsConstructor
@Table(name = "pedido")
public class modeloPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private modeloUsuario usuario;

    @Column(name = "direccion_entrega")
    private String direccionEntrega;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fecha_pedido")
    private Date fechaPedido;

    @OneToMany(mappedBy = "pedido")
    private Set<modeloDetallePedido> detallePedidos;

}