package com.iesfernandowirtz.ClothesVault.interfaz;

import com.iesfernandowirtz.ClothesVault.modelo.modeloDetallePedido;
import com.iesfernandowirtz.ClothesVault.modelo.modeloPedido;

import java.util.List;
import java.util.Map;

public interface interfazDetallePedido {
    public List<Map<String,Object>> listar();
    public List<Map<String,Object>> listar(int id);
    public modeloDetallePedido add(modeloDetallePedido dp);
    public modeloDetallePedido edit(modeloDetallePedido dp);
    public void delete(int id);

    List<modeloDetallePedido> buscarDetallesPorPedido(modeloPedido pedido);
}
