package com.iesfernandowirtz.ClothesVault.interfaz;

import com.iesfernandowirtz.ClothesVault.modelo.modeloCategoria;
import com.iesfernandowirtz.ClothesVault.modelo.modeloProducto;

import java.util.List;
import java.util.Map;

public interface interfazCategoria {
    public List<Map<String,Object>> listar();
    public List<Map<String,Object>> listar(int id);
    public modeloCategoria add(modeloCategoria c);
    public modeloCategoria edit(modeloCategoria c);
    public void delete(int id);

    // Nuevos métodos
    List<modeloCategoria> listarCategorias();
}
