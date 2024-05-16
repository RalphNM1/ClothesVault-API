package com.iesfernandowirtz.ClothesVault.controlador;


import com.iesfernandowirtz.ClothesVault.modelo.modeloUsuario;
import com.iesfernandowirtz.ClothesVault.servicio.servicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/usuarios")
@CrossOrigin(origins = "http://localhost:8080") // Permitir solicitudes desde localhost:8080
public class controladorUsuario {

    @Autowired
    servicioUsuario serviciousuario;

    @GetMapping("/listar")
    public List<Map<String, Object>> listar() {
        return serviciousuario.listar();

    }

    @GetMapping("/listarXEmail/{email}")
    public List<Map<String, Object>> listar(@PathVariable String email) {
        return serviciousuario.listar(email);
    }

    @PostMapping("/insertar")
    public String addUsuario(@RequestBody modeloUsuario u) {


        int r = serviciousuario.add(u);

        if (r == 0) {
            return "No se ha registrado";

        } else {
            return "Se ha registrado";
        }
    }
}
