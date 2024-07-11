package com.loja.Games.Repository;

import com.loja.Games.Entity.Pedido;
import com.loja.Games.Entity.Usuario;
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
public class PedidoDao {
    private final String sqlInsert = "INSERT INTO PEDIDO  (JOGO, USUARIO) " +
            "VALUES (?, ?)";
    private final String sqlDelete = "Delete from PEDIDO WHERE Id = ?";

    //private final String sqlSelect = "select ID, JOGO, USUARIO from PEDIDO";

    private final String  sqlSelect = "SELECT J.NOME AS NOME_JOGO, C.NOME AS NOME_CATEGORIA, J.PRECO AS PRECO " +
            "FROM PEDIDO P " +
            "INNER JOIN JOGO J ON P.JOGO = J.ID " +
            "INNER JOIN CATEGORIA C ON C.ID = J.CATEGORIA ";

    private final   String sqlUpdate = "UPDATE PEDIDO SET JOGO = ? WHERE ID = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //   public Pedido incluir(Pedido p) throws Exception {
///        validarPedido(p, true);
//        try (Connection con = jdbcTemplate.getDataSource().getConnection();
//             PreparedStatement ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
//            ps.setInt(1, p.getJogo().getId());
//            ps.setString(2, p.getUsuario().getNome());
//            int result = ps.executeUpdate();

//           throw new Exception("Erro no Cadastro.");
//        }
//    }

//    private void validarPedido(Pedido p, boolean inserting) throws Exception {
//      if (p.getJogo() == null) {
//            throw new Exception("Carinho vazio");
//        }

        //Verificar se a Categoria existe
        //TODO
//    }


    public Pedido getPedidoById(int id) throws Exception {
        List<Pedido> pedidos = listar();

        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                return pedido;
            }
        }
        return null;
    }

    public List<Pedido> listar() throws Exception {
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlSelect);) {
            List<Pedido> lista = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Pedido p = new Pedido();
                    //p.setId(rs.getInt("id"));
                    p.setNomeJogo(rs.getString("NOME_JOGO"));
                    p.setNomeCategoria(rs.getString("NOME_CATEGORIA"));
                    p.setPreco(rs.getFloat("PRECO"));
                    lista.add(p);
                }
            }
            return lista;
        }
    }

    public Pedido deletar(int id) throws Exception{
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlDelete)) {
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            if (result == 1) {
                System.out.println("Pedido Excluido com sucesso:" + this.getPedidoById(id).getId());
                return null;
            }
            throw new Exception("Erro ao Excluir pedido.");
        }
    }

//    public Pedido atualizar(int id, Pedido p) throws Exception{
//        try (Connection con = jdbcTemplate.getDataSource().getConnection();
//             PreparedStatement ps = con.prepareStatement(sqlUpdate)) {
//            ps.setString(1, p.getJogo().getNome());
//            ps.setInt(6,p.getId());

//            int result = ps.executeUpdate();
//            if (result == 1) {
//                System.out.println("Pedido Atualizado com sucesso:" + this.getPedidoById(id));
//                return null;
//            }
//            throw new Exception("Erro ao atualizar");
//        }
//    }
}
