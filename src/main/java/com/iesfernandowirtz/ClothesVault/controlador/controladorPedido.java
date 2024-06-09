package com.iesfernandowirtz.ClothesVault.controlador;

import com.iesfernandowirtz.ClothesVault.modelo.modeloDetallePedido;
import com.iesfernandowirtz.ClothesVault.modelo.modeloPedido;
import com.iesfernandowirtz.ClothesVault.modelo.modeloUsuario;


import com.iesfernandowirtz.ClothesVault.servicio.servicioDetallePedido;
import com.iesfernandowirtz.ClothesVault.servicio.servicioPedido;

import com.iesfernandowirtz.ClothesVault.servicio.servicioUsuario;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/pedidos")
@CrossOrigin(origins = "http://localhost:8080")
public class controladorPedido {

    @Autowired
    private servicioPedido servicioPedido;

    @Autowired
    private servicioUsuario servicioUsuario;

    @Autowired
    private servicioDetallePedido servicioDetallePedido;

    @PostMapping("/{idPedido}/agregarDetalle")
    public ResponseEntity<String> agregarDetallePedido(@PathVariable Long idPedido, @RequestBody modeloDetallePedido detallePedido) {
        try {
            // Obtener el pedido existente
            modeloPedido pedido = servicioPedido.obtenerPedidoPorId(idPedido);
            if (pedido == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el pedido con el ID proporcionado");
            }

            // Establecer la referencia al pedido en el detalle del pedido
            detallePedido.setPedido(pedido);

            // Guardar el detalle del pedido en la base de datos
            servicioDetallePedido.guardarDetallePedido(detallePedido);

            return ResponseEntity.ok("Detalle del pedido agregado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar el detalle del pedido");
        }
    }
    @Transactional
    @PostMapping("/{idPedido}/{idProducto}/actualizarCantidad")
    public ResponseEntity<String> actualizarCantidadEnBD(@PathVariable Long idPedido, @PathVariable Long idProducto, @RequestParam int nuevaCantidad) {
        try {
            servicioPedido.actualizarCantidadEnBD(idPedido, idProducto, nuevaCantidad);
            return ResponseEntity.ok("Cantidad actualizada en la base de datos");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la cantidad en la base de datos: " + e.getMessage());
        }
    }

    @Transactional
    @DeleteMapping("/{idPedido}/{idProducto}/eliminarProducto")
    public ResponseEntity<String> eliminarProductoDeBD(@PathVariable Long idPedido, @PathVariable Long idProducto) {
        try {
            servicioPedido.eliminarProductoDeBD(idPedido, idProducto);
            return ResponseEntity.ok("Producto eliminado de la base de datos");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el producto de la base de datos: " + e.getMessage());
        }
    }

}