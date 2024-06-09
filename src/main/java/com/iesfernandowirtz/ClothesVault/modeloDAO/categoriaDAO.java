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



}
