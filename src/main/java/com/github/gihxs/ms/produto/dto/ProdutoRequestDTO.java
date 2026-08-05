package com.github.gihxs.ms.produto.dto;

import com.github.gihxs.ms.produto.entities.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProdutoRequestDTO {

    @NotBlank(message = "Campo nome é obrigatório")
    @Size(min = 3, max = 100, message = "O campo nome deve ter entre 3 e 100 caracters")
    private String nome;

    @NotBlank(message = "Campo descrição é obrigatório")
    @Size(min = 10, message = "O campo descrição deve ter no mínimo 10 caracteres")
    private String descricao;

    @NotNull(message = "O campo valor é obrigatório")
    @Positive(message = "O campo valor deve ser um número positivo e maior que zero")
    private Double valor;

    @NotNull(message = "Campo categoria é requerido")
     private Long categoriaId;

    public ProdutoRequestDTO(Produto produto) {
        nome = produto.getNome();
        descricao = produto.getDescricao();
        valor = produto.getValor();
        categoriaId = produto.getCategoria().getId();
    }
}
