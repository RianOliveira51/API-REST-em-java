package com.loja.Games.Controller;

import com.loja.Games.Entity.Usuario;
import com.loja.Games.Repository.UsuarioDao;
import com.loja.Games.Retorno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
public class UsuarioController {

    @Autowired
    UsuarioDao dao;
    private Map<Integer, Usuario> usuarios = new TreeMap<>();

    @GetMapping("/usuario/listar")
    List<Usuario> all() throws Exception{
        return dao.listar();
    }

    @DeleteMapping("/usuario/{id}")
    public Retorno deletePessoa(@PathVariable Integer id) {
        try {
            return new Retorno(dao.deletar(id));
        } catch(Exception ex) {
            return new Retorno(ex.getMessage());
        }
    }

    @PostMapping("/usuario/cadastrar")
    public Retorno incluir(@RequestBody Usuario p) throws Exception {
        try {
            return new Retorno(dao.incluir(p));
        } catch (Exception ex) {
            return new Retorno(ex.getMessage());
        }
    }

    @PostMapping("/usuario/login")
    public Retorno login(@RequestBody Usuario u) throws Exception {
        try {
            if (u == null || u.getNome() == null) {
                return new Retorno("Dados imcompletos");
            }
            Usuario uBD = dao.getUsuarioByName(u.getNome());
            if (uBD == null) {
                return new Retorno("Usuário não cadastrado");
            }
            if (uBD.getSenha().equals(u.getSenha())) {
                return new Retorno(uBD);
            } else {
                return new Retorno("Senha incorreta");
            }
        } catch (Exception ex) {
            return new Retorno(ex.getMessage());
        }
    }

    @PutMapping("/usuario/{id}")
    public Usuario replaceLivro( @PathVariable Integer id, @RequestBody Usuario newUsuario) throws Exception {
        return dao.atualizar(id, newUsuario);
    }
}
