package com.loja.Games.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    private int id;
    private String nomeJogo;
    private String nomeCategoria;
    private Float preco;
}
