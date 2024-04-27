package com.iesfernandowirtz.ClothesVault.controlador;

import com.iesfernandowirtz.ClothesVault.servicio.servicioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/productos")
@CrossOrigin(origins = "http://localhost:8080") // Permitir solicitudes desde localhost:8080
public class controladorProducto {

    @Autowired
    servicioProducto servicioProducto;

    @GetMapping("/listar")
    public List<Map<String, Object>> listar() {
        return servicioProducto.listar();

    }
}
