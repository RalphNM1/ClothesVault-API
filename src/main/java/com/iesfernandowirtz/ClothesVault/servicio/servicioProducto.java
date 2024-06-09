package com.iesfernandowirtz.ClothesVault.servicio;

import com.iesfernandowirtz.ClothesVault.interfaz.interfazProducto;
import com.iesfernandowirtz.ClothesVault.modelo.modeloCategoria;
import com.iesfernandowirtz.ClothesVault.modelo.modeloDetallePedido;
import com.iesfernandowirtz.ClothesVault.modelo.modeloPedido;
import com.iesfernandowirtz.ClothesVault.modelo.modeloProducto;
import com.iesfernandowirtz.ClothesVault.modeloDAO.DetallePedidoDAO;
import com.iesfernandowirtz.ClothesVault.modeloDAO.PedidoDAO;
import com.iesfernandowirtz.ClothesVault.modeloDAO.productoDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.util.List;
import java.util.Map;

@Service
public class servicioProducto implements interfazProducto {
    private static final Logger logger = Logger.getLogger(servicioProducto.class.getName());

    @Autowired
    productoDAO dao;

    @Autowired
    DetallePedidoDAO detallePedidoDAO;

    @Autowired
    PedidoDAO pedidoDAO;

    @Override
    public List<Map<String, Object>> listar() {
        return dao.listar();
    }


    @Override
    public List<Map<String, Object>> listar(int id) {
        return List.of();
    }

    @Override
    public modeloProducto add(modeloProducto p) {
        return null;
    }

    @Override
    public modeloProducto edit(modeloProducto p) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    public List<String> listarMarcas() {
        return dao.listarMarcasDeProveedores();
    }

    public List<modeloProducto> getProductosPorCategoria(Long idCategoria) {
        return dao.getProductosPorCategoria(idCategoria);
    }

    public byte[] getProductImage(Long productId) {
        return dao.getProductImage(productId);
    }

    public List<modeloProducto> getProductosPorProveedor(String nombreProveedor) {
        return dao.getProductosPorProveedor(nombreProveedor);
    }

    public List<modeloProducto> getProductosPorCategoriaYProveedor(Long idCategoria, String nombreProveedor) {
        return dao.getProductosPorCategoriaYProveedor(idCategoria, nombreProveedor);
    }

    @Transactional
    public void agregarProductoAlCarrito(Long idPedido, Long idProducto, int cantidad) {
        try {
            // Obtener el pedido existente
            modeloPedido pedido = pedidoDAO.buscarPorId(idPedido);
            if (pedido == null) {
                // Manejar el caso en que no se encuentre el pedido
                throw new IllegalArgumentException("Pedido no encontrado con ID: " + idPedido);
            }

            // Verificar si el producto ya está en el carrito
            modeloDetallePedido detalleExistente = detallePedidoDAO.buscarPorPedidoYProducto(idPedido, idProducto);

            if (detalleExistente != null) {
                // Si el producto ya está en el carrito, actualizar el detalle existente
                int nuevaCantidad = detalleExistente.getCantidad() + cantidad;
                if (nuevaCantidad <= 0) {
                    // Si la nueva cantidad es menor o igual a cero, eliminar el detalle de pedido
                    detallePedidoDAO.eliminar(detalleExistente.getId());
                } else {
                    // Si la nueva cantidad es mayor que cero, actualizar el detalle existente
                    double nuevoPrecioTotal = detalleExistente.getProducto().getPrecio() * nuevaCantidad;
                    detalleExistente.setCantidad(nuevaCantidad);
                    detalleExistente.setPrecioTotal(nuevoPrecioTotal);
                    detallePedidoDAO.actualizar(detalleExistente);
                }
            } else {
                // Si el producto no está en el carrito, crear un nuevo detalle de pedido
                if (cantidad > 0) {
                    modeloProducto producto = dao.obtenerProductoPorId(idProducto);
                    if (producto == null) {
                        // Manejar el caso en que no se encuentre el producto
                        throw new IllegalArgumentException("Producto no encontrado con ID: " + idProducto);
                    }
                    modeloDetallePedido nuevoDetalle = new modeloDetallePedido();
                    nuevoDetalle.setPedido(pedido);
                    nuevoDetalle.setProducto(producto);
                    nuevoDetalle.setCantidad(cantidad);
                    nuevoDetalle.setPrecioTotal(producto.getPrecio() * cantidad);
                    detallePedidoDAO.guardar(nuevoDetalle);
                }
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            // Capturar excepciones específicas y devolver un mensaje de error claro
            logger.severe("Error al agregar producto al carrito: " + e.getMessage());
            throw e; // Relanzar la excepción para que se maneje en el controlador
        } catch (Exception e) {
            // Capturar cualquier otra excepción y registrarla para su posterior análisis
            logger.severe("Error inesperado al agregar producto al carrito." + e.getMessage());
            throw new RuntimeException("Error inesperado al agregar producto al carrito. Por favor, inténtalo de nuevo más tarde.");
        }
    }




}


