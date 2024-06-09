package com.iesfernandowirtz.ClothesVault.modeloDAO;

import com.iesfernandowirtz.ClothesVault.modelo.modeloDetallePedido;
import com.iesfernandowirtz.ClothesVault.modelo.modeloPedido;
import com.iesfernandowirtz.ClothesVault.modelo.modeloProducto;
import com.iesfernandowirtz.ClothesVault.modelo.modeloDetallePedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.List;

@Repository
public class DetallePedidoDAO {

    @Autowired
    private JdbcTemplate template;


    public List<modeloDetallePedido> buscarDetallesPorPedido(modeloPedido pedido) {
        String sql = "SELECT dp.id, dp.cantidad, dp.precio_total, p.id as producto_id, p.nombre, p.talla, p.descripcion, p.precio, p.stock, p.imagen " +
                "FROM detalle_pedido dp INNER JOIN producto p ON dp.producto_id = p.id WHERE dp.pedido_id = ?";
        return template.query(sql, new Object[]{pedido.getId()}, (rs, rowNum) -> {
            modeloDetallePedido detalle = new modeloDetallePedido();
            detalle.setId(rs.getLong("id"));
            detalle.setPedido(pedido);
            detalle.setCantidad(rs.getInt("cantidad"));
            detalle.setPrecioTotal(rs.getDouble("precio_total"));

            modeloProducto producto = new modeloProducto();
            producto.setId(rs.getLong("producto_id"));
            producto.setNombre(rs.getString("nombre"));
            producto.setTalla(rs.getString("talla"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setPrecio(rs.getDouble("precio"));
            producto.setStock(rs.getInt("stock"));
            producto.setImagen(rs.getBytes("imagen")); // Asignar la imagen

            detalle.setProducto(producto);

            return detalle;
        });
    }
    public void guardar(modeloDetallePedido detallePedido) {
        String sql = "INSERT INTO detalle_pedido (pedido_id, producto_id, cantidad, precio_total) VALUES (?, ?, ?, ?)";
        template.update(sql, detallePedido.getPedido().getId(), detallePedido.getProducto().getId(), detallePedido.getCantidad(), detallePedido.getPrecioTotal());
    }

    public void edit(modeloDetallePedido detalleExistente) {
        String sql = "UPDATE detalle_pedido SET cantidad = ?, precio_total = ? WHERE id = ?";
        template.update(sql, detalleExistente.getCantidad(), detalleExistente.getPrecioTotal(), detalleExistente.getId());
    }

    public modeloDetallePedido buscarPorPedidoYProducto(Long idPedido, Long idProducto) {
        try {
            System.out.println("Buscando detalle de pedido para idPedido: " + idPedido + " y idProducto: " + idProducto);
            String sql = "SELECT * FROM detalle_pedido WHERE pedido_id = ? AND producto_id = ?";
            modeloDetallePedido detallePedido = template.queryForObject(sql, new Object[]{idPedido, idProducto}, (rs, rowNum) -> {
                modeloDetallePedido detalle = new modeloDetallePedido();
                detalle.setId(rs.getLong("id"));
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setPrecioTotal(rs.getDouble("precio_total"));
                // Consulta adicional para obtener el producto
                Long productoId = rs.getLong("producto_id");
                modeloProducto producto = obtenerProductoPorId(productoId);
                detalle.setProducto(producto);
                return detalle;
            });
            return detallePedido;
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No se encontró ningún detalle de pedido para idPedido: " + idPedido + " y idProducto: " + idProducto);
            return null;
        } catch (DataAccessException e) {
            System.out.println("Error al buscar detalle de pedido: " + e.getMessage());
            return null;
        }
    }
    private modeloProducto obtenerProductoPorId(Long productoId) {
        String sql = "SELECT * FROM producto WHERE id = ?";
        return template.queryForObject(sql, new Object[]{productoId}, (rs, rowNum) -> {
            modeloProducto producto = new modeloProducto();
            producto.setId(rs.getLong("id"));
            producto.setNombre(rs.getString("nombre"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setPrecio(rs.getDouble("precio"));
            producto.setStock(rs.getInt("stock"));
            producto.setImagen(rs.getBytes("imagen"));
            producto.setTalla(rs.getString("talla"));
            // Mapear otras propiedades si es necesario
            return producto;
        });
    }
    // Método para actualizar un detalle de pedido en la base de datos
    public void actualizar(modeloDetallePedido detalle) {
        String sql = "UPDATE detalle_pedido SET cantidad = :cantidad, precio_total = :precioTotal WHERE id = :id";

        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("cantidad", detalle.getCantidad());
        paramMap.addValue("precioTotal", detalle.getPrecioTotal());
        paramMap.addValue("id", detalle.getId());

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(template);
        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    public void eliminar(Long idDetallePedido) {
        String sql = "DELETE FROM detalle_pedido WHERE id = ?";
        template.update(sql, idDetallePedido);
    }

    public void actualizarCantidad(Long idPedido, Long idProducto, int nuevaCantidad) {
        String sql = "UPDATE detalle_pedido SET cantidad = ? WHERE pedido_id = ? AND producto_id = ?";
        template.update(sql, nuevaCantidad, idPedido, idProducto);
    }

    public void eliminarPorPedidoYProducto(Long idPedido, Long idProducto) {
        String sql = "DELETE FROM detalle_pedido WHERE pedido_id = ? AND producto_id = ?";
        template.update(sql, idPedido, idProducto);
    }


    public List<modeloDetallePedido> obtenerDetallesPedido(Long idPedido) {
        String sql = "SELECT dp.id, dp.cantidad, dp.precio_total, p.id as producto_id, p.nombre, p.talla, p.descripcion, p.precio, p.stock, p.imagen " +
                "FROM detalle_pedido dp INNER JOIN producto p ON dp.producto_id = p.id WHERE dp.pedido_id = ?";
        return template.query(sql, new Object[]{idPedido}, (rs, rowNum) -> {
            modeloDetallePedido detalle = new modeloDetallePedido();
            detalle.setId(rs.getLong("id"));
            // Cambia aquí para instanciar modeloPedido directamente con la ID del pedido
            detalle.setPedido(new modeloPedido(idPedido));
            detalle.setCantidad(rs.getInt("cantidad"));
            detalle.setPrecioTotal(rs.getDouble("precio_total"));

            modeloProducto producto = new modeloProducto();
            producto.setId(rs.getLong("producto_id"));
            producto.setNombre(rs.getString("nombre"));
            producto.setTalla(rs.getString("talla"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setPrecio(rs.getDouble("precio"));
            producto.setStock(rs.getInt("stock"));
            producto.setImagen(rs.getBytes("imagen")); // Asignar la imagen

            detalle.setProducto(producto);

            return detalle;
        });
    }

}
