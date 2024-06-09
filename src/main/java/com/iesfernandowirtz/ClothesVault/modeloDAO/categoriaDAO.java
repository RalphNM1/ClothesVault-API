package com.iesfernandowirtz.ClothesVault.modeloDAO;

import com.iesfernandowirtz.ClothesVault.interfaz.interfazCategoria; // Importación de la interfaz de categoría
import com.iesfernandowirtz.ClothesVault.modelo.modeloCategoria; // Importación del modelo de categoría
import com.iesfernandowirtz.ClothesVault.modelo.modeloProducto; // Importación del modelo de producto
import com.iesfernandowirtz.ClothesVault.modelo.modeloProveedor; // Importación del modelo de proveedor
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate; // Importación de JdbcTemplate para acceso a la base de datos
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository // Anotación que indica que esta clase sirve como componente de acceso a datos
public class categoriaDAO implements interfazCategoria {

    @Autowired // Inyección de dependencias de JdbcTemplate
    JdbcTemplate templete;

    // Método para listar todas las categorías
    @Override
    public List<Map<String, Object>> listar() {
        List<Map<String, Object>> lista = templete.queryForList("select * from categoria"); // Consulta SQL para obtener todas las categorías
        return lista; // Devuelve la lista de categorías
    }

    // Método para listar categorías por ID (no implementado)
    @Override
    public List<Map<String, Object>> listar(int id) {
        return List.of(); // Devuelve una lista vacía (no implementado)
    }

    // Método para agregar una nueva categoría (no implementado)
    @Override
    public modeloCategoria add(modeloCategoria c) {
        return null; // Devuelve nulo (no implementado)
    }

    // Método para editar una categoría (no implementado)
    @Override
    public modeloCategoria edit(modeloCategoria c) {
        return null; // Devuelve nulo (no implementado)
    }

    // Método para eliminar una categoría por ID (no implementado)
    @Override
    public void delete(int id) {
        // No realiza ninguna operación (no implementado)
    }

}
