package com.iesfernandowirtz.ClothesVault.controlador;

import com.iesfernandowirtz.ClothesVault.modelo.modeloCategoria;
import com.iesfernandowirtz.ClothesVault.modelo.modeloProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import  com.iesfernandowirtz.ClothesVault.servicio.servicioCategoria;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/categorias")
@CrossOrigin(origins = "http://localhost:8080")
public class controladorCategoria {

    @Autowired
    servicioCategoria servicioCategoria;

    @GetMapping("/listar")
    public List<Map<String,Object>> listar() {
        return servicioCategoria.listar();
    }




}
