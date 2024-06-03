package com.iesfernandowirtz.ClothesVault.modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Data
@NoArgsConstructor
@Table(name = "categoria")
public class modeloCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<modeloProducto> productos = new ArrayList<modeloProducto>();


    public modeloCategoria(Long id, String nombre, List<modeloProducto> productos) {
        this.id = id;
        this.nombre = nombre;
        this.productos = productos;
    }

    public modeloCategoria(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

}