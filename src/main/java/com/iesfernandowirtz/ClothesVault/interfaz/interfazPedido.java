package com.iesfernandowirtz.ClothesVault.interfaz;

import com.iesfernandowirtz.ClothesVault.modelo.modeloPedido;
import com.iesfernandowirtz.ClothesVault.modelo.modeloProducto;

import java.util.List;
import java.util.Map;

public interface interfazPedido {
    public List<Map<String,Object>> listar();
    public List<Map<String,Object>> listar(int id);
    public modeloPedido add(modeloPedido p);
    public modeloPedido edit(modeloPedido p);
    public void delete(int id);
}
