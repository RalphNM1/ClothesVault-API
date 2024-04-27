package com.iesfernandowirtz.ClothesVault.servicio;

import com.iesfernandowirtz.ClothesVault.interfaz.interfazUsuario;
import com.iesfernandowirtz.ClothesVault.modelo.modeloUsuario;
import com.iesfernandowirtz.ClothesVault.modeloDAO.usuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class servicioUsuario implements interfazUsuario {

    @Autowired
    usuarioDAO dao;

    @Override
    public List<Map<String, Object>> listar() {

        return dao.listar();
    }

    @Override
    public List<Map<String, Object>> listar(int id) {
        return List.of();
    }

    @Override
    public modeloUsuario add(modeloUsuario u) {
        return null;
    }

    @Override
    public modeloUsuario edit(modeloUsuario u) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
