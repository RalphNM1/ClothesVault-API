package com.iesfernandowirtz.ClothesVault.controlador;

import com.iesfernandowirtz.ClothesVault.modelo.modeloProducto;
import com.iesfernandowirtz.ClothesVault.servicio.servicioProducto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/productos")
@CrossOrigin(origins = "http://localhost:8080")
public class controladorProducto {

    // Inyección de dependencias para el servicio de producto
    @Autowired
    servicioProducto servicioProducto;

    // Método para listar todos los productos
    @GetMapping("/listar")
    public List<Map<String, Object>> listar() {
        return servicioProducto.listar();
    }

    // Método para obtener todas las marcas de productos disponibles
    @GetMapping("/marcas")
    public List<String> listarMarcas() {
        return servicioProducto.listarMarcas();
    }

    // Método para obtener productos filtrados por categoría y/o proveedor
    @GetMapping("/porCategoria")
    public List<modeloProducto> getProductosPorCategoria(@RequestParam(required = false) Long idCategoria, @RequestParam(required = false) String nombreProveedor) {
        if (idCategoria != null && nombreProveedor != null) {
            // Filtrar productos por categoría y proveedor
            return servicioProducto.getProductosPorCategoriaYProveedor(idCategoria, nombreProveedor);
        } else if (idCategoria != null) {
            // Filtrar productos por categoría
            return servicioProducto.getProductosPorCategoria(idCategoria);
        } else if (nombreProveedor != null) {
            // Filtrar productos por proveedor
            return servicioProducto.getProductosPorProveedor(nombreProveedor);
        } else {
            // Si no se especifica categoría ni proveedor, retorna una lista vacía
            return new ArrayList<>();
        }
    }

    // Método para obtener productos filtrados por proveedor
    @GetMapping("/porProveedor")
    public List<modeloProducto> getProductosPorProveedor(@RequestParam String nombreProveedor) {
        return servicioProducto.getProductosPorProveedor(nombreProveedor);
    }

    // Método para obtener la imagen de un producto por su ID
    @GetMapping("/{id}/imagen")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
        byte[] imagen = servicioProducto.getProductImage(id);
        if (imagen != null && imagen.length > 0) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG); // Ajusta el tipo MIME según el formato de la imagen
            headers.setContentLength(imagen.length);
            return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
        } else {
            // Si no se encuentra la imagen, devuelve un error 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Método para agregar un producto al carrito de compras
    @PostMapping("/agregarProducto")
    public ResponseEntity<Void> agregarProductoAlCarrito(@RequestBody Map<String, Long> body, @RequestParam int cantidad) {
        Long idPedido = body.get("idPedido");
        Long idProducto = body.get("idProducto");

        if (idPedido != null && idProducto != null) {
            try {
                // Intenta agregar el producto al carrito
                servicioProducto.agregarProductoAlCarrito(idPedido, idProducto, cantidad);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (IllegalArgumentException | IllegalStateException e) {
                // Si hay algún error, devuelve un error 500
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Si los parámetros son inválidos, devuelve un error 400
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
