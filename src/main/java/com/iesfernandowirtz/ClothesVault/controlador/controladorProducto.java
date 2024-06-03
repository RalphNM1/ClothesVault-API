package com.iesfernandowirtz.ClothesVault.controlador;

import com.iesfernandowirtz.ClothesVault.modelo.modeloCategoria;
import com.iesfernandowirtz.ClothesVault.modelo.modeloProducto;
import com.iesfernandowirtz.ClothesVault.servicio.servicioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/productos")
@CrossOrigin(origins = "http://localhost:8080")
public class controladorProducto {

    @Autowired
    servicioProducto servicioProducto;

    private static String UPLOADED_FOLDER = "C:\\Users\\ralph\\Documents\\NetBeansProjects\\CRUDClothesVault\\src\\main\\resources\\Imagenes";

    @GetMapping("/listar")
    public List<Map<String, Object>> listar() {
        return servicioProducto.listar();
    }


    @GetMapping("/marcas")
    public List<String> listarMarcas() {
        return servicioProducto.listarMarcas();
    }

    @GetMapping("/imagenes/{nombreImagen:.+}")
    public ResponseEntity<Resource> obtenerImagen(@PathVariable String nombreImagen) {
        Path filePath = Paths.get(UPLOADED_FOLDER).resolve(nombreImagen).normalize();
        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
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


    @GetMapping("/porCategoria")
    public List<modeloProducto> getProductosPorCategoria(@RequestParam(required = false) Long idCategoria, @RequestParam(required = false) String marca) {
        if (idCategoria != null && marca != null) {
            // Si se proporciona tanto idCategoria como marca, filtrar los productos por categoría y marca
            return servicioProducto.getProductosPorCategoria(idCategoria, marca);
        } else if (idCategoria != null) {
            // Si se proporciona solo idCategoria, obtener los productos por categoría
            return servicioProducto.getProductosPorCategoria(idCategoria);
        } else if (marca != null) {
            // Si se proporciona solo marca, obtener los productos por marca
            return servicioProducto.getProductosPorMarca(marca);
        } else {
            // Si no se proporciona ni idCategoria ni marca, retornar una lista vacía o manejarlo según tu lógica de negocio
            return new ArrayList<>();
        }
    }



}
