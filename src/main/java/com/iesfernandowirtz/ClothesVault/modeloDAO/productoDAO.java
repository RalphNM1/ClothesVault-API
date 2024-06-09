package com.iesfernandowirtz.ClothesVault.modeloDAO;

import com.iesfernandowirtz.ClothesVault.interfaz.interfazProducto;
import com.iesfernandowirtz.ClothesVault.modelo.modeloCategoria;
import com.iesfernandowirtz.ClothesVault.modelo.modeloProducto;
import com.iesfernandowirtz.ClothesVault.modelo.modeloProveedor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class productoDAO implements interfazProducto {

    @Autowired
    JdbcTemplate template;

    @Override
    public List<Map<String, Object>> listar() {
        List<Map<String, Object>> lista = template.queryForList("select * from producto");
        return lista;
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


    @Override
    public List<modeloProducto> getProductosPorCategoria(Long idCategoria) {
        String sql = "SELECT * FROM producto WHERE categoria_id = ?";
        return template.query(sql, new Object[]{idCategoria}, (rs, rowNum) -> {
            return mapearProducto(rs);
        });
    }

    public List<String> listarMarcasDeProveedores() {
        String sql = "SELECT DISTINCT p.nombre FROM proveedor p JOIN producto pr ON p.id = pr.proveedor_id";
        return template.query(sql, (rs, rowNum) -> rs.getString("nombre"));
    }
    private modeloProducto mapearProducto(ResultSet rs) throws SQLException {
        modeloProducto producto = new modeloProducto();
        producto.setId(rs.getLong("id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setStock(rs.getInt("stock"));
        producto.setTalla(rs.getString("talla"));
        producto.setImagen(rs.getBytes("imagen"));
        producto.setImagenBase64(Base64.getEncoder().encodeToString(producto.getImagen()));
        return producto;
    }

    public byte[] getProductImage(Long productId) {
        String sql = "SELECT imagen FROM producto WHERE id = ?";
        return template.queryForObject(sql, new Object[]{productId}, (rs, rowNum) -> rs.getBytes("imagen"));
    }

    public List<modeloProducto> getProductosPorProveedor(String nombreProveedor) {
        String sql = "SELECT * FROM producto WHERE proveedor_id = (SELECT id FROM proveedor WHERE nombre = ?)";
        return template.query(sql, new Object[]{nombreProveedor}, (rs, rowNum) -> mapearProducto(rs));
    }

    public List<modeloProducto> getProductosPorCategoriaYProveedor(Long idCategoria, String nombreProveedor) {
        String sql = "SELECT * FROM producto WHERE categoria_id = ? AND proveedor_id = (SELECT id FROM proveedor WHERE nombre = ?)";
        return template.query(sql, new Object[]{idCategoria, nombreProveedor}, (rs, rowNum) -> mapearProducto(rs));
    }


    public modeloProducto obtenerProductoPorId(Long idProducto) {
        try {
            System.out.println("Buscando producto por id: " + idProducto);
            String sql = "SELECT * FROM producto WHERE id = ?";
            return template.queryForObject(sql, new Object[]{idProducto}, (rs, rowNum) -> {
                modeloProducto producto = new modeloProducto();
                producto.setId(rs.getLong("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));
                producto.setImagen(rs.getBytes("imagen"));
                producto.setTalla(rs.getString("talla"));

                //  mapear las relaciones de muchos a uno
                // como categoria y proveedor si deseas utilizarlas.

                //  relación ManyToOne con modeloCategoria:
                 modeloCategoria categoria = new modeloCategoria();
                 categoria.setId(rs.getLong("categoria_id"));
                 producto.setCategoria(categoria);

                //relación ManyToOne con modeloProveedor:
                 modeloProveedor proveedor = new modeloProveedor();
                 proveedor.setId(rs.getLong("proveedor_id"));
                 producto.setProveedor(proveedor);

                return producto;
            });
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No se encontró ningún producto para id: " + idProducto);
            return null;
        } catch (DataAccessException e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
            return null;
        }
    }
}
