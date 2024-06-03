package com.iesfernandowirtz.ClothesVault.interfaz;

import com.iesfernandowirtz.ClothesVault.modelo.modeloCategoria;
import com.iesfernandowirtz.ClothesVault.modelo.modeloProducto;

import java.util.List;
import java.util.Map;

public interface interfazProducto {
    public List<Map<String,Object>> listar();
    public List<Map<String,Object>> listar(int id);
    public modeloProducto add(modeloProducto p);
    public modeloProducto edit(modeloProducto p);
    public void delete(int id);

    List<String> listarMarcas();

    List<modeloProducto> getProductosPorCategoria(Long idCategoria);
    List<modeloProducto> getProductosPorCategoria(Long idCategoria, String marca);
    List<modeloProducto> getProductosPorMarca(String marca);
}
