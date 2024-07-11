package com.loja.Games.Repository;

import com.loja.Games.Entity.Categoria;
import com.loja.Games.Entity.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoriaDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String sqlSelect = "select ID, NOME from CATEGORIA";
    public List<Categoria> listar() throws Exception {
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlSelect);) {
            List<Categoria> lista = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Categoria c = new Categoria();
                    c.setId(rs.getInt("id jogo"));
                    c.setId(rs.getInt("id usuario"));
                    lista.add(c);
                }
            }
            return lista;
        }
    }
}
