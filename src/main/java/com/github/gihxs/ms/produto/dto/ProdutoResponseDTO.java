package com.github.gihxs.ms.produto.dto;

import com.github.gihxs.ms.produto.entities.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProdutoResponseDTO {

    private Long id;


    private String nome;


    private String descricao;


    private Double valor;


    private CategoriaResponseDTO categoria;

    public ProdutoResponseDTO(Produto produto) {
        id = produto.getId();
        nome = produto.getNome();
        descricao = produto.getDescricao();
        valor = produto.getValor();
        categoria = new CategoriaResponseDTO(produto.getCategoria());
    }
}
