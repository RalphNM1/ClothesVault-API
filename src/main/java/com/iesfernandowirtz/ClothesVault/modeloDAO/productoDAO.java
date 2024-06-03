package com.iesfernandowirtz.ClothesVault.modeloDAO;

import com.iesfernandowirtz.ClothesVault.interfaz.interfazProducto;
import com.iesfernandowirtz.ClothesVault.modelo.modeloProducto;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<String> listarMarcas() {
        return template.query("select distinct marca from producto", (rs, rowNum) -> rs.getString("marca"));
    }

    @Override
    public List<modeloProducto> getProductosPorCategoria(Long idCategoria) {
        String sql = "SELECT * FROM producto WHERE categoria_id = ?";
        return template.query(sql, new Object[]{idCategoria}, (rs, rowNum) -> {
            return mapearProducto(rs);
        });
    }

    @Override
    public List<modeloProducto> getProductosPorCategoria(Long idCategoria, String marca) {
        String sql = "SELECT * FROM producto WHERE categoria_id = ? AND marca = ?";
        return template.query(sql, new Object[]{idCategoria, marca}, (rs, rowNum) -> {
            return mapearProducto(rs);
        });
    }

    @Override
    public List<modeloProducto> getProductosPorMarca(String marca) {
        String sql = "SELECT * FROM producto WHERE marca = ?";
        return template.query(sql, new Object[]{marca}, (rs, rowNum) -> {
            return mapearProducto(rs);
        });
    }

    private modeloProducto mapearProducto(ResultSet rs) throws SQLException {
        modeloProducto producto = new modeloProducto();
        producto.setId(rs.getLong("id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setMarca(rs.getString("marca"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setStock(rs.getInt("stock"));
        producto.setTalla(rs.getString("talla"));
        producto.setImagen_url(rs.getString("imagen_url"));
        return producto;
    }
}
