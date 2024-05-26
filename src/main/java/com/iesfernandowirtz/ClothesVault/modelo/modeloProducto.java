package com.iesfernandowirtz.ClothesVault.modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

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

    private String imagen_url; // Cambiado de byte[] a String

    private String talla;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private modeloProveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private modeloCategoria categoria;

    @OneToMany(mappedBy = "producto")
    private Set<modeloDetallePedido> detallePedidos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImagenUrl() {
        return imagen_url;
    }

    public void setImagenUrl(String imagen_url) {
        this.imagen_url = this.imagen_url;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public modeloProveedor getProveedor() {
        return proveedor;
    }

    public String getImagen_url() {
        return imagen_url;
    }

    public void setImagen_url(String imagen_url) {
        this.imagen_url = imagen_url;
    }

    public void setProveedor(modeloProveedor proveedor) {
        this.proveedor = proveedor;
    }

    public modeloCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(modeloCategoria categoria) {
        this.categoria = categoria;
    }

    public Set<modeloDetallePedido> getDetallePedidos() {
        return detallePedidos;
    }

    public void setDetallePedidos(Set<modeloDetallePedido> detallePedidos) {
        this.detallePedidos = detallePedidos;
    }
}
