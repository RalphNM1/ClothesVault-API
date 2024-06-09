package com.iesfernandowirtz.ClothesVault.modeloDAO;

import com.iesfernandowirtz.ClothesVault.interfaz.interfazUsuario;
import com.iesfernandowirtz.ClothesVault.modelo.modeloUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    private modeloUsuario mapRowToUsuario(ResultSet rs) throws SQLException {
        modeloUsuario usuario = new modeloUsuario();
        usuario.setId(rs.getLong("id"));
        usuario.setEmail(rs.getString("email"));
        usuario.setContrasenha(rs.getString("contrasenha"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setApellido1(rs.getString("apellido1"));
        usuario.setApellido2(rs.getString("apellido2"));
        usuario.setDireccion(rs.getString("direccion"));
        usuario.setCp(rs.getInt("cp"));
        return usuario;
    }

    public modeloUsuario buscarPorCorreo(String email) {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        try {
            // Registro de depuración para verificar la ejecución de la consulta SQL
            System.out.println("Ejecutando consulta SQL: " + sql);
            System.out.println("Parámetro email: " + email);

            // Ejecutar la consulta SQL y mapear el resultado a un objeto modeloUsuario
            return templete.queryForObject(sql, new Object[]{email}, (rs, rowNum) -> mapRowToUsuario(rs));
        } catch (EmptyResultDataAccessException e) {
            // Registro de depuración en caso de que no se encuentre ningún resultado
            System.out.println("No se encontraron resultados para el correo electrónico: " + email);
            return null;
        } catch (DataAccessException e) {
            // Registro de depuración en caso de que ocurra algún error de acceso a datos
            System.out.println("Error al ejecutar la consulta SQL: " + e.getMessage());
            return null;
        }
    }


}
