package com.iesfernandowirtz.ClothesVault.modeloDAO;

import com.iesfernandowirtz.ClothesVault.interfaz.interfazPedido;
import com.iesfernandowirtz.ClothesVault.modelo.modeloPedido;
import com.iesfernandowirtz.ClothesVault.modelo.modeloUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Repository // Indica que esta clase es un componente de acceso a datos
public class PedidoDAO implements interfazPedido {

    @Autowired // Inyección de dependencias de JdbcTemplate
    JdbcTemplate template;

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
        String sql = "INSERT INTO pedido (usuario_id, estado, fecha_pedido) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder(); // Para recuperar el ID generado automáticamente
        try {
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, p.getUsuario().getId());
                ps.setString(2, p.getEstado());
                ps.setDate(3, p.getFechaPedido());
                return ps;
            }, keyHolder);
            p.setId(keyHolder.getKey().longValue()); // Establecer el ID generado en el objeto modeloPedido
        } catch (DataAccessException e) {
            // Manejar cualquier excepción de acceso a datos aquí
            e.printStackTrace();
            return null; // O lanzar una excepción personalizada
        }
        return p;
    }

    @Override
    public modeloPedido edit(modeloPedido p) {
        return null;
    }

    @Override
    public void delete(int id) {
        // Método sin implementar para eliminar un pedido por su ID
    }

    // Método auxiliar para mapear una fila de la tabla pedido a un objeto modeloPedido
    private modeloPedido mapRowToPedido(ResultSet rs) throws SQLException {
        modeloPedido pedido = new modeloPedido();
        pedido.setId(rs.getLong("id"));
        pedido.setEstado(rs.getString("estado"));
        pedido.setFechaPedido(rs.getDate("fecha_pedido"));
        // No es necesario mapear el usuario_id, ya que este campo se almacena en la entidad modeloPedido como una relación con el modeloUsuario.
        return pedido;
    }

    // Método para buscar un pedido en estado "Procesando" por usuario
    public modeloPedido buscarPedidoProcesandoPorUsuario(modeloUsuario usuario) {
        String sql = "SELECT * FROM pedido WHERE usuario_id = ? AND estado = 'Procesando'";
        try {
            return template.queryForObject(sql, new Object[]{usuario.getId()}, (rs, rowNum) -> mapRowToPedido(rs));
        } catch (EmptyResultDataAccessException e) {
            return null; // Devolver null si no se encuentra ningún pedido "Procesando" para el usuario
        }
    }

    // Método para buscar un pedido por su ID
    public modeloPedido buscarPorId(Long idPedido) {
        String sql = "SELECT * FROM pedido WHERE id = ?";
        try {
            return template.queryForObject(sql, new Object[]{idPedido}, (rs, rowNum) -> {
                modeloPedido pedido = new modeloPedido();
                pedido.setId(rs.getLong("id"));
                pedido.setEstado(rs.getString("estado"));
                pedido.setFechaPedido(rs.getDate("fecha_pedido"));
                // Aquí no necesitas mapear el usuario_id, ya que este campo se almacena en la entidad modeloPedido como una relación con el modeloUsuario.
                return pedido;
            });
        } catch (EmptyResultDataAccessException e) {
            return null; // Devolver null si no se encuentra ningún pedido con ese ID
        }
    }

    // Método para actualizar el precio total de un producto en un pedido
    public void actualizarPrecioTotal(Long idPedido, Long idProducto, double precioTotal) {
        String sql = "UPDATE detalle_pedido SET precio_total = ? WHERE pedido_id = ? AND producto_id = ?";
        template.update(sql, precioTotal, idPedido, idProducto);
    }

    // Método para actualizar un pedido en la base de datos
    public void actualizarPedido(modeloPedido p) {
        String sql = "UPDATE pedido SET estado = ?, fecha_pedido = ? WHERE id = ?";
        template.update(sql, p.getEstado(), p.getFechaPedido(), p.getId());
    }
}
