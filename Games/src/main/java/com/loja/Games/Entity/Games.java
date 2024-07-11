package com.loja.Games.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Games {
    private int id;
    private String nome;
    private Categoria categoria;
    private String faixaetaria;
    private Plataforma plataforma;
    private Float preco;
}
