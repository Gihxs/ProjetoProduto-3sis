package com.github.gihxs.ms.produto.dto;

import com.github.gihxs.ms.produto.entities.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoriaRequestDTO {

    @NotBlank(message = "Campo nome não pode ser vazio, nulo ou em branco")
    @Size(min = 3, max = 100, message = "O Nome deve ter entre 3 e 100 caracteres")
    @Schema(example = "Ferramentas")
    private String nome;

    public CategoriaRequestDTO(Categoria categoria){
        nome = categoria.getNome();
    }
}
