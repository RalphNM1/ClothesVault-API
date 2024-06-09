package com.iesfernandowirtz.ClothesVault.controlador;

import com.iesfernandowirtz.ClothesVault.modelo.modeloDetallePedido;
import com.iesfernandowirtz.ClothesVault.modelo.modeloPedido;
import com.iesfernandowirtz.ClothesVault.servicio.servicioDetallePedido;
import com.iesfernandowirtz.ClothesVault.servicio.servicioPedido;
import com.iesfernandowirtz.ClothesVault.servicio.servicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/detallepedidos")
@CrossOrigin(origins = "http://localhost:8080")
public class controladorDetallePedido {

    // Inyección de dependencias para los servicios necesarios
    @Autowired
    private servicioUsuario servicioUsuario;

    @Autowired
    private servicioPedido servicioPedido;

    @Autowired
    private servicioDetallePedido servicioDetallePedido;

    // Método para obtener los detalles de un pedido
    @GetMapping("/obtenerDetallesPedido")
    public ResponseEntity<?> obtenerDetallesPedido(@RequestParam("idPedido") Long idPedido) {
        // Verificar si se proporciona un ID de pedido válido
        if (idPedido == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El parámetro idPedido es requerido");
        }

        // Buscar el pedido por su ID
        modeloPedido pedido = servicioPedido.obtenerPedidoPorId(idPedido);

        // Verificar si el pedido existe
        if (pedido == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado");
        }

        // Buscar los detalles del pedido por el pedido encontrado
        List<modeloDetallePedido> detallesPedido = servicioDetallePedido.buscarDetallesPorPedido(pedido);
        System.out.println("Detalles del pedido " + idPedido + ": " + detallesPedido.size());
        return ResponseEntity.ok(detallesPedido);
    }

}
