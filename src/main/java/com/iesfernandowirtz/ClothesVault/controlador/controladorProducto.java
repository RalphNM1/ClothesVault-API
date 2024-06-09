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

    @Autowired
    servicioProducto servicioProducto;

    @GetMapping("/listar")
    public List<Map<String, Object>> listar() {
        return servicioProducto.listar();
    }


    @GetMapping("/marcas")
    public List<String> listarMarcas() {
        return servicioProducto.listarMarcas();
    }


    @GetMapping("/porCategoria")
    public List<modeloProducto> getProductosPorCategoria(@RequestParam(required = false) Long idCategoria, @RequestParam(required = false) String nombreProveedor) {
        if (idCategoria != null && nombreProveedor != null) {
            // Si se proporciona tanto idCategoria como nombreProveedor, filtrar los productos por categoría y nombre del proveedor
            return servicioProducto.getProductosPorCategoriaYProveedor(idCategoria, nombreProveedor);
        } else if (idCategoria != null) {
            // Si se proporciona solo idCategoria, obtener los productos por categoría
            return servicioProducto.getProductosPorCategoria(idCategoria);
        } else if (nombreProveedor != null) {
            // Si se proporciona solo nombreProveedor, obtener los productos por nombre del proveedor
            return servicioProducto.getProductosPorProveedor(nombreProveedor);
        } else {
            // Si no se proporciona ni idCategoria ni nombreProveedor, retornar una lista vacía o manejarlo según tu lógica de negocio
            return new ArrayList<>();
        }
    }

    @GetMapping("/porProveedor")
    public List<modeloProducto> getProductosPorProveedor(@RequestParam String nombreProveedor) {
        return servicioProducto.getProductosPorProveedor(nombreProveedor);
    }

    @GetMapping("/{id}/imagen")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
        byte[] imagen = servicioProducto.getProductImage(id);
        if (imagen != null && imagen.length > 0) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG); // Ajusta el tipo MIME según el formato de la imagen
            headers.setContentLength(imagen.length);
            return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/agregarProducto")
    public ResponseEntity<Void> agregarProductoAlCarrito(@RequestBody Map<String, Long> body, @RequestParam int cantidad) {
        Long idPedido = body.get("idPedido");
        Long idProducto = body.get("idProducto");

        if (idPedido != null && idProducto != null) {
            try {
                servicioProducto.agregarProductoAlCarrito(idPedido, idProducto, cantidad);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (IllegalArgumentException | IllegalStateException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




}
