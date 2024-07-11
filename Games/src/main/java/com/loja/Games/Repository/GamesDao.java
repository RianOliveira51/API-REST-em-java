package com.loja.Games.Repository;

import com.loja.Games.Entity.Games;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GamesDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String sqlInsert = "INSERT INTO JOGO  (NOME, CATEGORIA, FAIXA_ETARIA, PLATAFORMA, PRECO) " +
            "VALUES (?, ?, ?, ?, ?)";
    private final String sqlDelete = "Delete from JOGO WHERE Id = ?";

    private final String sqlSelect = "select NOME, CATEGORIA, FAIXA_ETARIA, PLATAFORMA, PRECO from JOGO";

    private final   String sqlUpdate = "UPDATE JOGO SET NOME = ?, CATEGORIA = ?, FAIXA_ETARIA = ?, PLATAFORMA =?, PRECO = ? WHERE ID = ?";


    public Games incluir(Games g) throws Exception {
        validarGame(g, true);
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, g.getNome());
            ps.setInt(2, g.getCategoria().getId());
            ps.setString(3, g.getFaixaetaria());
            ps.setInt(4, g.getPlataforma().getId());
            ps.setFloat(5, g.getPreco());
            int result = ps.executeUpdate();
            if (result == 1) {
                ResultSet tableKeys = ps.getGeneratedKeys(); //ID Gerado.
                tableKeys.next();
                g.setId(tableKeys.getInt(1));

                System.out.println("Game Cadastrado com sucesso:" + g.getNome());
                return g;
            }
            throw new Exception("Erro no Cadastro.");
        }
    }

    private void validarGame(Games g, boolean inserting) throws Exception {
        if (g.getNome() == null || g.getNome().trim().isEmpty()) {
            throw new Exception("Nome do jogo não pode ser branco");
        }
        g.setNome(g.getNome().trim()); //Limpa os espaços no começo ou fim

        //Verificar se o nome existe
        String query = "select id from GAMES where nome = ?";
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(query);) {
            ps.setString(1, g.getNome());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    if (inserting || rs.getInt("id") != g.getId()) {
                        throw new Exception("Jogo já existe");
                    }
                }
            }
        }
        //Verificar se a Categoria existe
        //TODO
    }

    public Games getGamesById(int id) throws Exception {
        List<Games> games = listar();

        for (Games game : games) {
            if (game.getId() == id) {
                return game;
            }
        }
        return null;
    }

    public List<Games> listar() throws Exception {
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlSelect);) {
            List<Games> lista = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Games g = new Games();
                    g.setNome(rs.getString("nome"));
                    g.setPreco(rs.getFloat("Preco"));
                    lista.add(g);
                }
            }
            return lista;
        }
    }

    public Games deletar(int id) throws Exception{
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlDelete)) {
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            if (result == 1) {
                System.out.println("Jogo Excluido com sucesso:" + this.getGamesById(id).getNome());
                return null;
            }
            throw new Exception("Erro ao Excluir.");
        }
    }

    public Games atualizar(int id, Games g) throws Exception{
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlUpdate)) {
            ps.setString(1, g.getNome());
            ps.setInt(2, g.getCategoria().getId());
            ps.setString(3, g.getFaixaetaria());
            ps.setInt(4, g.getPlataforma().getId());
            ps.setFloat(5, g.getPreco());
            ps.setInt(6,g.getId());

            int result = ps.executeUpdate();
            if (result == 1) {
                System.out.println("Jogo Atualizado com sucesso:" + this.getGamesById(id));
                return null;
            }
            throw new Exception("Erro ao atualizar");
        }
    }

}
