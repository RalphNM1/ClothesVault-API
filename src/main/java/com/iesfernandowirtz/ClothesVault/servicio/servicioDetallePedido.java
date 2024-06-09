package com.iesfernandowirtz.ClothesVault.servicio;

import com.iesfernandowirtz.ClothesVault.interfaz.interfazDetallePedido;
import com.iesfernandowirtz.ClothesVault.modelo.modeloDetallePedido;
import com.iesfernandowirtz.ClothesVault.modeloDAO.DetallePedidoDAO;
import com.iesfernandowirtz.ClothesVault.modelo.modeloPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service // Indica que esta clase es un componente de servicio de la aplicación
public class servicioDetallePedido implements interfazDetallePedido {

    @Autowired // Inyección de dependencias de DetallePedidoDAO
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
        // Método sin implementar para eliminar un detalle de pedido por su ID
    }

    @Override
    public List<modeloDetallePedido> buscarDetallesPorPedido(modeloPedido pedido) {
        // Método para buscar detalles de pedido por el pedido asociado utilizando el método correspondiente en DetallePedidoDAO
        return dao.buscarDetallesPorPedido(pedido);
    }

    public void guardarDetallePedido(modeloDetallePedido detallePedido) {
        // Método para guardar un detalle de pedido utilizando el método correspondiente en DetallePedidoDAO
        dao.guardar(detallePedido);
    }
}
