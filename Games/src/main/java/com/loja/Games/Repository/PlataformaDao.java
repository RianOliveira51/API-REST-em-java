package com.loja.Games.Repository;

import com.loja.Games.Entity.Categoria;
import com.loja.Games.Entity.Plataforma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlataformaDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String sqlSelect = "select ID, NOME from PLATAFORMA";
    public List<Plataforma> listar() throws Exception {
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlSelect);) {
            List<Plataforma> lista = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Plataforma a = new Plataforma();
                    a.setId(rs.getInt("id plataforma"));
                    a.setId(rs.getInt("id nome da plataforma"));
                    lista.add(a);
                }
            }
            return lista;
        }
    }
}
