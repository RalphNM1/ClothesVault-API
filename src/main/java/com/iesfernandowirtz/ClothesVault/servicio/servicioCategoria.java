package com.iesfernandowirtz.ClothesVault.servicio;

import com.iesfernandowirtz.ClothesVault.interfaz.interfazCategoria;
import com.iesfernandowirtz.ClothesVault.modelo.modeloCategoria;
import com.iesfernandowirtz.ClothesVault.modeloDAO.categoriaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service // Indica que esta clase es un componente de servicio de la aplicación
public class servicioCategoria implements interfazCategoria {
    @Autowired // Inyección de dependencias de categoriaDAO
    categoriaDAO dao;

    @Override
    public List<Map<String,Object>> listar() {
        // Método para listar todas las categorías utilizando el método correspondiente en categoriaDAO
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
        // Método sin implementar para eliminar una categoría por su ID
    }
}
