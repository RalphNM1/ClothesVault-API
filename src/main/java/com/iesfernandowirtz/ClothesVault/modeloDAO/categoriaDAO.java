package com.iesfernandowirtz.ClothesVault.modeloDAO;

import com.iesfernandowirtz.ClothesVault.interfaz.interfazCategoria;
import com.iesfernandowirtz.ClothesVault.modelo.modeloCategoria;
import com.iesfernandowirtz.ClothesVault.modelo.modeloProducto;
import com.iesfernandowirtz.ClothesVault.modelo.modeloProveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class categoriaDAO implements interfazCategoria {

    @Autowired
    JdbcTemplate templete;

    @Override
    public List<Map<String, Object>> listar() {
        List<Map<String, Object>> lista = templete.queryForList("select * from categoria");
        return lista;
    }

    @Override
    public List<Map<String, Object>> listar(int id) {
        return List.of();
    }

    @Override
    public modeloCategoria add(modeloCategoria c) {
        return null;
    }

    @Override
    public modeloCategoria edit(modeloCategoria c) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<modeloCategoria> listarCategorias() {
        // Consulta SQL para obtener categorías con productos asociados
        String sql = "SELECT c.id AS categoria_id, c.nombre AS categoria_nombre, " +
                "p.id AS producto_id, p.nombre AS producto_nombre " +
                "FROM categoria c " +
                "LEFT JOIN producto p ON c.id = p.categoria_id";

        // Ejecutar la consulta y mapear los resultados a instancias de modeloCategoria
        return templete.query(sql, (rs, rowNum) -> {
            Long categoriaId = rs.getLong("categoria_id");
            String categoriaNombre = rs.getString("categoria_nombre");
            Long productoId = rs.getLong("producto_id");
            String productoNombre = rs.getString("producto_nombre");

            // Crear una nueva categoría si no existe aún en la lista
            modeloCategoria categoria = new modeloCategoria(categoriaId, categoriaNombre);
            if (categoria.getProductos() == null) {
                categoria.setProductos(new ArrayList<>());
            }

            // Agregar el producto a la lista de productos de la categoría
            if (productoId != null) {
                modeloProducto producto = new modeloProducto();
                producto.setId(productoId);
                producto.setNombre(productoNombre);
                categoria.getProductos().add(producto);
            }

            return categoria;
        });
    }

}
