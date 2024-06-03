package com.iesfernandowirtz.ClothesVault.servicio;

import com.iesfernandowirtz.ClothesVault.interfaz.interfazCategoria;
import com.iesfernandowirtz.ClothesVault.modelo.modeloCategoria;
import com.iesfernandowirtz.ClothesVault.modelo.modeloProducto;
import com.iesfernandowirtz.ClothesVault.modeloDAO.categoriaDAO;
import com.iesfernandowirtz.ClothesVault.modeloDAO.productoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class servicioCategoria implements interfazCategoria {
    @Autowired
    categoriaDAO dao;

    @Override
    public List<Map<String,Object>> listar() {
        return dao.listar();
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
        return dao.listarCategorias();
    }


}
