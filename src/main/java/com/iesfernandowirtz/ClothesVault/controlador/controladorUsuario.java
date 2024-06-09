package com.iesfernandowirtz.ClothesVault.controlador;

import com.iesfernandowirtz.ClothesVault.modelo.modeloDetallePedido;
import com.iesfernandowirtz.ClothesVault.modelo.modeloPedido;
import com.iesfernandowirtz.ClothesVault.modelo.modeloUsuario;
import com.iesfernandowirtz.ClothesVault.servicio.servicioDetallePedido;
import com.iesfernandowirtz.ClothesVault.servicio.servicioPedido;
import com.iesfernandowirtz.ClothesVault.servicio.servicioProducto;
import com.iesfernandowirtz.ClothesVault.servicio.servicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/usuarios")
@CrossOrigin(origins = "http://localhost:8080") // Permitir solicitudes desde localhost:8080
public class controladorUsuario {

    @Autowired
    private servicioUsuario servicioUsuario;

    @Autowired
    private servicioPedido servicioPedido;

    @Autowired
    private servicioDetallePedido servicioDetallePedido;


    @Autowired
    private servicioProducto servicioProducto;

    @GetMapping("/listar")
    public List<Map<String, Object>> listar() {
        return servicioUsuario.listar();
    }

    @GetMapping("/listarXEmail/{email}")
    public List<Map<String, Object>> listar(@PathVariable String email) {
        return servicioUsuario.listar(email);
    }

    @PostMapping("/insertar")
    public String addUsuario(@RequestBody modeloUsuario u) {
        int r = servicioUsuario.add(u);
        if (r == 0) {
            return "No se ha registrado";
        } else {
            return "Se ha registrado";
        }
    }

    @PostMapping("/iniciarSesion")
    public ResponseEntity<?> iniciarSesion(@RequestBody modeloUsuario usuario) {
        modeloUsuario usuarioEncontrado = servicioUsuario.buscarPorCorreo(usuario.getEmail());

        if (usuarioEncontrado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        modeloPedido pedidoProcesando = servicioPedido.buscarPedidoProcesandoPorUsuario(usuarioEncontrado);

        if (pedidoProcesando != null) {
            List<modeloDetallePedido> detallesPedido = servicioDetallePedido.buscarDetallesPorPedido(pedidoProcesando);
            System.out.println("ID del pedido en procesamiento: " + pedidoProcesando.getId());
            Map<String, Object> response = new HashMap<>();
            response.put("detallesPedido", detallesPedido);
            response.put("idPedido", pedidoProcesando.getId());
            return ResponseEntity.ok(response);
        } else {
            modeloPedido nuevoPedido = servicioPedido.crearPedidoProcesandoParaUsuario(usuarioEncontrado);
            System.out.println("ID del nuevo pedido creado: " + nuevoPedido.getId());
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Se ha creado un nuevo pedido en estado 'Procesando' para el usuario");
            response.put("idPedido", nuevoPedido.getId());
            return ResponseEntity.ok(response);
        }
    }


}
