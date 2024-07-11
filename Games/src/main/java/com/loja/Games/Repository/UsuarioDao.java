package com.loja.Games.Repository;

import com.loja.Games.Entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioDao {
    private final String sqlInsert = "INSERT INTO USUARIO  (NOME, SENHA, EMAIL, TELEFONE) " +
            "VALUES (?, ?, ?, ?)";
    private final String sqlDelete = "Delete from USUARIO WHERE Id = ?";

    private final String sqlSelect = "select ID, NOME, SENHA, TELEFONE, EMAIL from USUARIO";

    private final   String sqlUpdate = "UPDATE USUARIO SET NOME = ?, SENHA = ?, EMAIL = ?, TELEFONE = ? WHERE ID = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Usuario incluir(Usuario u) throws Exception {
        validarUsuario(u, true);
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getSenha());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getTelefone());
            int result = ps.executeUpdate();
            if (result == 1) {
                ResultSet tableKeys = ps.getGeneratedKeys(); //ID Gerado.
                tableKeys.next();
                u.setId(tableKeys.getInt(1));

                System.out.println("Usuario Cadastrado com sucesso:" + u.getNome());
                return u;
            }
            throw new Exception("Erro no Cadastro.");
        }
    }

    private void validarUsuario(Usuario u, boolean inserting) throws Exception {
        if (u.getNome() == null || u.getNome().trim().isEmpty()) {
            throw new Exception("Nome não pode ser branco");
        }
        u.setNome(u.getNome().trim()); //Limpa os espaços no começo ou fim

        //Verificar se o nome existe
        String query = "select id from USUARIO where nome = ?";
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(query);) {
            ps.setString(1, u.getNome());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    if (inserting || rs.getInt("id") != u.getId()) {
                        throw new Exception("Usuario já existe");
                    }
                }
            }
        }
        //Verificar se a Categoria existe
        //TODO
    }


    public Usuario getUsuarioById(int id) throws Exception {
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlSelect + " where id = ?");) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    Usuario u = getUsuario(rs);
                    return u;

                }
            }
        }
        return null;
    }

    public Usuario getUsuarioByName(String name) throws Exception {
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlSelect + " where nome = ?");) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    Usuario u = getUsuario(rs);
                    return u;

                }
            }
        }
        return null;
    }

    public List<Usuario> listar() throws Exception {
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlSelect);) {
            List<Usuario> lista = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Usuario u = getUsuario(rs);
                    lista.add(u);

                }
            }
            return lista;
        }
    }

    private static Usuario getUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNome(rs.getString("nome"));
        u.setSenha(rs.getString("senha"));
        u.setTelefone(rs.getString("email"));
        u.setEmail(rs.getString("telefone"));
        return u;
    }

    public Usuario deletar(int id) throws Exception{
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlDelete)) {
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            if (result == 1) {
                System.out.println("Usuario Excluido com sucesso:" + this.getUsuarioById(id).getNome());
                return null;
            }
            throw new Exception("Erro ao Excluir.");
        }
    }

    public Usuario atualizar(int id, Usuario u) throws Exception{
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlUpdate)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getSenha());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getTelefone());
            ps.setInt(5,u.getId());

            int result = ps.executeUpdate();
            if (result == 1) {
                System.out.println("Usuario Atualizado com sucesso:" + this.getUsuarioById(id).getNome());
                return null;
            }
            throw new Exception("Erro ao atualizar");
        }
    }

}
