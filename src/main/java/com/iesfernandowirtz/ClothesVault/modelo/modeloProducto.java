package com.iesfernandowirtz.ClothesVault.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Base64;
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
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "stock")
    private Integer stock;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "talla")
    private String talla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private modeloCategoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id")
    private modeloProveedor proveedor;

    @OneToMany(mappedBy = "producto")
    private Set<modeloDetallePedido> detallePedidos;

    @Transient
    private String imagenBase64;

    public String getImagenBase64() {
        return imagen != null ? Base64.getEncoder().encodeToString(imagen) : null;
    }

    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
        this.imagen = imagenBase64 != null ? Base64.getDecoder().decode(imagenBase64) : null;
    }
}
