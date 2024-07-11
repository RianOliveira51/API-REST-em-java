package com.loja.Games.Controller;

import com.loja.Games.Repository.CategoriaDao;
import com.loja.Games.Retorno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoriaController {

    @Autowired
    CategoriaDao dao;

    @GetMapping("/categoria")
    public Retorno listar() throws Exception {
        try {
            return new Retorno(dao.listar());
        } catch (Exception ex) {
            return new Retorno(ex.getMessage());
        }
    }
}
