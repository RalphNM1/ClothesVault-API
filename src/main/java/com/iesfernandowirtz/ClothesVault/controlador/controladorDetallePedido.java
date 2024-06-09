package com.iesfernandowirtz.ClothesVault.controlador;

import com.iesfernandowirtz.ClothesVault.modelo.modeloDetallePedido;
import com.iesfernandowirtz.ClothesVault.modelo.modeloPedido;
import com.iesfernandowirtz.ClothesVault.modelo.modeloUsuario;
import com.iesfernandowirtz.ClothesVault.servicio.servicioDetallePedido;
import com.iesfernandowirtz.ClothesVault.servicio.servicioPedido;
import com.iesfernandowirtz.ClothesVault.servicio.servicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/detallepedidos")
@CrossOrigin(origins = "http://localhost:8080")
public class controladorDetallePedido {

    @Autowired
    private servicioUsuario servicioUsuario;

    @Autowired
    private servicioPedido servicioPedido;

    @Autowired
    private servicioDetallePedido servicioDetallePedido;

    @GetMapping("/obtenerDetallesPedido")
    public ResponseEntity<?> obtenerDetallesPedido(@RequestParam("idPedido") Long idPedido) {
        if (idPedido == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El parámetro idPedido es requerido");
        }

        modeloPedido pedido = servicioPedido.obtenerPedidoPorId(idPedido);

        if (pedido == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado");
        }

        List<modeloDetallePedido> detallesPedido = servicioDetallePedido.buscarDetallesPorPedido(pedido);
        System.out.println("Detalles del pedido " + idPedido + ": " + detallesPedido.size());
        return ResponseEntity.ok(detallesPedido);
    }

}
