package com.iesfernandowirtz.ClothesVault.servicio;
import com.iesfernandowirtz.ClothesVault.interfaz.interfazDetallePedido;
import com.iesfernandowirtz.ClothesVault.modelo.modeloDetallePedido;
import com.iesfernandowirtz.ClothesVault.modeloDAO.DetallePedidoDAO;
import com.iesfernandowirtz.ClothesVault.modelo.modeloPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class servicioDetallePedido implements interfazDetallePedido{

    @Autowired
    private DetallePedidoDAO dao;

    @Override
    public List<Map<String, Object>> listar() {
        return List.of();
    }

    @Override
    public List<Map<String, Object>> listar(int id) {
        return List.of();
    }

    @Override
    public modeloDetallePedido add(modeloDetallePedido dp) {
        return null;
    }

    @Override
    public modeloDetallePedido edit(modeloDetallePedido dp) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<modeloDetallePedido> buscarDetallesPorPedido(modeloPedido pedido) {
        return dao.buscarDetallesPorPedido(pedido);
    }

    public void guardarDetallePedido(modeloDetallePedido detallePedido) {
        dao.guardar(detallePedido);
    }


}
