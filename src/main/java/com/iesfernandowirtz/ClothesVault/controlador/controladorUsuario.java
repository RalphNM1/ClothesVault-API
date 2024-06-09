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
@CrossOrigin(origins = "http://localhost:8080")
public class controladorUsuario {

    // Inyección de dependencias para servicios
    @Autowired
    private servicioUsuario servicioUsuario;

    @Autowired
    private servicioPedido servicioPedido;

    @Autowired
    private servicioDetallePedido servicioDetallePedido;

    @Autowired
    private servicioProducto servicioProducto;

    // Método para listar usuarios
    @GetMapping("/listar")
    public List<Map<String, Object>> listar() {
        return servicioUsuario.listar();
    }

    // Método para listar usuarios por su email
    @GetMapping("/listarXEmail/{email}")
    public List<Map<String, Object>> listar(@PathVariable String email) {
        return servicioUsuario.listar(email);
    }

    // Método para insertar un nuevo usuario
    @PostMapping("/insertar")
    public String addUsuario(@RequestBody modeloUsuario u) {
        int r = servicioUsuario.add(u);
        if (r == 0) {
            return "No se ha registrado";
        } else {
            return "Se ha registrado";
        }
    }

    // Método para crear pedidos de usuario
    @PostMapping("/crearPedidos")
    public ResponseEntity<?> iniciarSesion(@RequestBody modeloUsuario usuario) {
        // Buscar usuario por correo
        modeloUsuario usuarioEncontrado = servicioUsuario.buscarPorCorreo(usuario.getEmail());

        // Si el usuario no existe, devolver error 404
        if (usuarioEncontrado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        // Buscar pedido en proceso por el usuario
        modeloPedido pedidoProcesando = servicioPedido.buscarPedidoProcesandoPorUsuario(usuarioEncontrado);

        // Si hay un pedido en proceso, devolver detalles del pedido
        if (pedidoProcesando != null) {
            List<modeloDetallePedido> detallesPedido = servicioDetallePedido.buscarDetallesPorPedido(pedidoProcesando);
            System.out.println("ID del pedido en procesamiento: " + pedidoProcesando.getId());
            Map<String, Object> response = new HashMap<>();
            response.put("detallesPedido", detallesPedido);
            response.put("idPedido", pedidoProcesando.getId());
            return ResponseEntity.ok(response);
        } else {
            // Si no hay un pedido en proceso, crear uno nuevo
            modeloPedido nuevoPedido = servicioPedido.crearPedidoProcesandoParaUsuario(usuarioEncontrado);
            System.out.println("ID del nuevo pedido creado: " + nuevoPedido.getId());
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Se ha creado un nuevo pedido en estado 'Procesando' para el usuario");
            response.put("idPedido", nuevoPedido.getId());
            return ResponseEntity.ok(response);
        }
    }
}
