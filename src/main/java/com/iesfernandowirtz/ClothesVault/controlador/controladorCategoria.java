package com.iesfernandowirtz.ClothesVault.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.iesfernandowirtz.ClothesVault.servicio.servicioCategoria; // Importación del servicio de categoría
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/categorias") // Mapeo de la ruta base para las operaciones relacionadas con categorías
@CrossOrigin(origins = "http://localhost:8080") // Configuración de CORS para permitir solicitudes desde localhost:8080
public class controladorCategoria {

    @Autowired // Inyección de dependencias del servicio de categoría
    servicioCategoria servicioCategoria;

    // Método para listar todas las categorías
    @GetMapping("/listar")
    public List<Map<String,Object>> listar() {
        return servicioCategoria.listar(); // Devuelve la lista de categorías obtenida del servicio
    }

}
