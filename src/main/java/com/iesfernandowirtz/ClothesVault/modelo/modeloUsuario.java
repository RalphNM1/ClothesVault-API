package com.iesfernandowirtz.ClothesVault.modelo;

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
@Table(name = "usuario")
public class modeloUsuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "contrasenha")
    private String contrasenha;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido1")
    private String apellido1;

    @Column(name = "apellido2")
    private String apellido2;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "cp")
    private Integer cp;

    @OneToMany(mappedBy = "usuario")
    private Set<modeloPedido> pedidos;


}
