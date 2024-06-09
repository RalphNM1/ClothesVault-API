package com.iesfernandowirtz.ClothesVault.servicio;

import com.iesfernandowirtz.ClothesVault.interfaz.interfazPedido;
import com.iesfernandowirtz.ClothesVault.modelo.modeloDetallePedido;
import com.iesfernandowirtz.ClothesVault.modelo.modeloPedido;
import com.iesfernandowirtz.ClothesVault.modelo.modeloUsuario;
import com.iesfernandowirtz.ClothesVault.modeloDAO.PedidoDAO;
import com.iesfernandowirtz.ClothesVault.modeloDAO.DetallePedidoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service // Indica que esta clase es un componente de servicio de la aplicación
public class servicioPedido implements interfazPedido {

    @Autowired // Inyección de dependencias de PedidoDAO
    private PedidoDAO dao;

    @Autowired // Inyección de dependencias de DetallePedidoDAO
    private DetallePedidoDAO detallePedidoDAO;

    // Definición de un logger para registrar información o errores
    private static final Logger logger = Logger.getLogger(servicioProducto.class.getName());

    @Override
    public List<Map<String, Object>> listar() {
        return List.of();
    }

    @Override
    public List<Map<String, Object>> listar(int id) {
        return List.of();
    }

    @Override
    public modeloPedido add(modeloPedido p) {
        return dao.add(p);
    }

    @Override
    public modeloPedido edit(modeloPedido p) {
        return null;
    }

    @Override
    public void delete(int id) {
        // Método sin implementar para eliminar un pedido por su ID
    }

    public modeloPedido buscarPedidoProcesandoPorUsuario(modeloUsuario usuario) {
        return dao.buscarPedidoProcesandoPorUsuario(usuario);
    }

    public modeloPedido crearPedidoProcesandoParaUsuario(modeloUsuario usuario) {
        // Crear un nuevo pedido para el usuario con estado "Procesando"
        modeloPedido pedido = new modeloPedido();
        pedido.setUsuario(usuario);
        pedido.setEstado("Procesando");
        pedido.setFechaPedido(Date.valueOf(LocalDate.now()));
        return dao.add(pedido);
    }

    public modeloPedido obtenerPedidoPorId(Long idPedido) {
        return dao.buscarPorId(idPedido);
    }

    @Transactional // Anotación para indicar que este método es transaccional
    public void actualizarCantidadEnBD(Long idPedido, Long idProducto, int nuevaCantidad) {
        try {
            detallePedidoDAO.actualizarCantidad(idPedido, idProducto, nuevaCantidad);
            recalcularPrecioTotal(idPedido,idProducto); // Recalcular el precio total después de actualizar la cantidad
        } catch (Exception e) {
            // Manejar la excepción según sea necesario
            logger.severe("Error al actualizar la cantidad en la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al actualizar la cantidad en la base de datos. Por favor, inténtalo de nuevo más tarde.");
        }
    }

    @Transactional // Anotación para indicar que este método es transaccional
    public void eliminarProductoDeBD(Long idPedido, Long idProducto) {
        try {
            detallePedidoDAO.eliminarPorPedidoYProducto(idPedido, idProducto);
        } catch (Exception e) {
            // Manejar la excepción según sea necesario
            logger.severe("Error al eliminar el producto de la base de datos: " + e.getMessage());
        }
    }

    private void recalcularPrecioTotal(Long idPedido, Long idProducto) {
        // Obtener los detalles del pedido
        List<modeloDetallePedido> detallesPedido = detallePedidoDAO.obtenerDetallesPedido(idPedido);

        double precioTotal = 0.0;

        // Calcular el precio total sumando el precio de cada detalle multiplicado por la cantidad
        for (modeloDetallePedido detalle : detallesPedido) {
            double precioProducto = detalle.getProducto().getPrecio();
            int cantidad = detalle.getCantidad();
            precioTotal += precioProducto * cantidad;
        }

        // Actualizar el precio total del pedido en la base de datos
        dao.actualizarPrecioTotal(idPedido, idProducto, precioTotal);
    }

    public boolean actualizarEstado(Long idPedido, String nuevoEstado) {
        modeloPedido pedido = dao.buscarPorId(idPedido);
        if (pedido != null) {
            pedido.setEstado(nuevoEstado);
            dao.actualizarPedido(pedido);
            return true;
        }
        return false;
    }
}
