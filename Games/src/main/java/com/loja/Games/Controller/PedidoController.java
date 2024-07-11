package com.loja.Games.Controller;

import com.loja.Games.Entity.Pedido;
import com.loja.Games.Entity.Usuario;
import com.loja.Games.Repository.PedidoDao;
import com.loja.Games.Repository.UsuarioDao;
import com.loja.Games.Retorno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
public class PedidoController {
    @Autowired
    PedidoDao dao;
    private Map<Integer, Pedido> pedidos = new TreeMap<>();

    @GetMapping("/pedidos/listar")
    List<Pedido> all() throws Exception{
        return dao.listar();
    }

    @DeleteMapping("/pedidos/{id}")
    public Retorno deletePedido(@PathVariable Integer id) {
        try {
            return new Retorno(dao.deletar(id));
        } catch(Exception ex) {
            return new Retorno(ex.getMessage());
        }
    }

//    @PostMapping("/pedidos/cadastrar")
//    public Retorno incluir(@RequestBody Pedido p) throws Exception {
//       try {
//            return new Retorno(dao.incluir(p));
//        } catch (Exception ex) {
//            return new Retorno(ex.getMessage());
//        }
//    }


//    @PutMapping("/pedido/{id}")
//    @CrossOrigin(origins="http://127.0.0.1:5500")
//    public Pedido replaceLivro( @PathVariable Integer id, @RequestBody Pedido newPedido) throws Exception {
//        return dao.atualizar(id, newPedido);
//    }

}
