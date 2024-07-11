package com.loja.Games.Controller;

import com.loja.Games.Entity.Games;
import com.loja.Games.Entity.Usuario;
import com.loja.Games.Repository.GamesDao;
import com.loja.Games.Repository.UsuarioDao;
import com.loja.Games.Retorno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
public class GamesController {

    @Autowired
    GamesDao dao;
    private Map<Integer, Games> games = new TreeMap<>();

    @GetMapping("/games/listar")
    List<Games> all() throws Exception{
        return dao.listar();
    }

    @DeleteMapping("/games/{id}")
    public Retorno deletePessoa(@PathVariable Integer id) {
        try {
            return new Retorno(dao.deletar(id));
        } catch(Exception ex) {
            return new Retorno(ex.getMessage());
        }
    }

    @PostMapping("/games/cadastrar")
    public Retorno incluir(@RequestBody Games g) throws Exception {
        try {
            return new Retorno(dao.incluir(g));
        } catch (Exception ex) {
            return new Retorno(ex.getMessage());
        }
    }

    @PostMapping("/games/login")
    public Retorno login(@RequestBody Games g) throws Exception {
        try {
            return new Retorno(dao.incluir(g));
        } catch (Exception ex) {
            return new Retorno(ex.getMessage());
        }
    }

    @PutMapping("/games/{id}")
    @CrossOrigin(origins="http://127.0.0.1:5500")
    public Games replaceLivro( @PathVariable Integer id, @RequestBody Games newGames) throws Exception {
        return dao.atualizar(id, newGames);
    }
}
