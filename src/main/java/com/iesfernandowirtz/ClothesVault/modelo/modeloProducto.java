package com.iesfernandowirtz.ClothesVault.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Entity
@Data
@NoArgsConstructor
@Table(name = "producto")
public class modeloProducto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String marca;
    private String imagen_url;
    private String talla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private modeloCategoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id")
    private modeloProveedor proveedor;

    @OneToMany(mappedBy = "producto")
    private Set<modeloDetallePedido> detallePedidos;

}
