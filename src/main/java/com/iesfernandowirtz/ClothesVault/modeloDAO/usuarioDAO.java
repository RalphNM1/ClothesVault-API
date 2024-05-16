package com.iesfernandowirtz.ClothesVault.modeloDAO;

import com.iesfernandowirtz.ClothesVault.interfaz.interfazUsuario;
import com.iesfernandowirtz.ClothesVault.modelo.modeloUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;



@Repository
public class usuarioDAO implements interfazUsuario {

    @Autowired
    JdbcTemplate templete;

    @Override
    public List<Map<String, Object>> listar() {
        List<Map<String,Object>>lista = templete.queryForList("select * from usuario");
        return lista;
    }

    @Override
    public List<Map<String, Object>> listar(String email) {
        String sql = "select * from usuario where email = ?";

        return templete.queryForList(sql, email);
    }

    @Override
    public int add(modeloUsuario u) {
        String sql = "insert into usuario(email,contrasenha,nombre,apellido1,apellido2,direccion,cp)values(?,?,?,?,?,?,?)";

        return  templete.update(sql,u.getEmail(),u.getContrasenha(),u.getNombre(),u.getApellido1(), u.getApellido2(), u.getDireccion(), u.getCp());
    }

    @Override
    public modeloUsuario edit(modeloUsuario u) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
