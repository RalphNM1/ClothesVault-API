package com.iesfernandowirtz.ClothesVault.servicio;

import com.iesfernandowirtz.ClothesVault.interfaz.interfazUsuario;
import com.iesfernandowirtz.ClothesVault.modelo.modeloUsuario;
import com.iesfernandowirtz.ClothesVault.modeloDAO.usuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service // Indica que esta clase es un componente de servicio de la aplicación
public class servicioUsuario implements interfazUsuario {

    @Autowired // Inyección de dependencias de usuarioDAO
    usuarioDAO dao;

    @Override
    public List<Map<String, Object>> listar() {
        // Método para listar todos los usuarios
        return dao.listar();
    }

    @Override
    public List<Map<String, Object>> listar(String email) {
        // Método para listar usuarios por su correo electrónico
        return dao.listar(email);
    }

    @Override
    public int add(modeloUsuario u) {
        // Método para agregar un nuevo usuario
        return dao.add(u);
    }

    @Override
    public modeloUsuario edit(modeloUsuario u) {
        // Método para editar un usuario (no implementado)
        return null;
    }

    @Override
    public void delete(int id) {
        // Método para eliminar un usuario por su ID (no implementado)
    }

    public modeloUsuario buscarPorCorreo(String email) {
        // Método para buscar un usuario por su correo electrónico
        return dao.buscarPorCorreo(email);
    }
}
