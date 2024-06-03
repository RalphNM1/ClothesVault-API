package com.iesfernandowirtz.ClothesVault.servicio;

import com.iesfernandowirtz.ClothesVault.interfaz.interfazProducto;
import com.iesfernandowirtz.ClothesVault.modelo.modeloCategoria;
import com.iesfernandowirtz.ClothesVault.modelo.modeloProducto;
import com.iesfernandowirtz.ClothesVault.modeloDAO.productoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class servicioProducto implements interfazProducto {

    @Autowired
    productoDAO dao;

    @Override
    public List<Map<String, Object>> listar() {

        return dao.listar();
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
        return dao.listarMarcas();
    }

    public List<modeloProducto> getProductosPorCategoria(Long idCategoria,String marca) {
        return dao.getProductosPorCategoria(idCategoria, marca);
    }

    public List<modeloProducto> getProductosPorMarca(String marca) {
        return dao.getProductosPorMarca(marca);
    }

    public List<modeloProducto> getProductosPorCategoria(Long idCategoria) {
        return dao.getProductosPorCategoria(idCategoria);
    }


}


