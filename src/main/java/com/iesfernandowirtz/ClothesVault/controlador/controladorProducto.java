package com.iesfernandowirtz.ClothesVault.controlador;

import com.iesfernandowirtz.ClothesVault.modelo.modeloProducto;
import com.iesfernandowirtz.ClothesVault.servicio.servicioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/productos")
@CrossOrigin(origins = "http://localhost:8080") // Permitir solicitudes desde localhost:8080
public class controladorProducto {

    @Autowired
    servicioProducto servicioProducto;

    private static String UPLOADED_FOLDER = "C:\\Users\\ralph\\Documents\\NetBeansProjects\\CRUDClothesVault\\src\\main\\resources\\Imagenes";

    @GetMapping("/listar")
    public List<Map<String, Object>> listar() {
        return servicioProducto.listar();

    }
    @GetMapping("/imagenes/{nombreImagen:.+}")
    public ResponseEntity<Resource> obtenerImagen(@PathVariable String nombreImagen) {
        Path filePath = Paths.get(UPLOADED_FOLDER).resolve(nombreImagen).normalize();
        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG) // Cambiar según el tipo de imagen
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

}
