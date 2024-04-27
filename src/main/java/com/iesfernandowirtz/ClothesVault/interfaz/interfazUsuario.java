package com.iesfernandowirtz.ClothesVault.interfaz;

import com.iesfernandowirtz.ClothesVault.modelo.modeloUsuario;

import java.util.List;
import java.util.Map;

public interface interfazUsuario {
    public List<Map<String,Object>> listar();
    public List<Map<String,Object>> listar(int id);
    public modeloUsuario add(modeloUsuario u);
    public modeloUsuario edit(modeloUsuario u);
    public void delete(int id);
}
