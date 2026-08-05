package com.github.gihxs.ms.produto.controller;

import com.github.gihxs.ms.produto.dto.ProdutoRequestDTO;
import com.github.gihxs.ms.produto.dto.ProdutoResponseDTO;
import com.github.gihxs.ms.produto.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    //forçando erro 500 para teste
    @Profile("teste")
    @GetMapping("/--demo/500")
    public String force500(){
        throw new RuntimeException("Erro 500 forçado para teste");
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> getAllProdutos(){

        List<ProdutoResponseDTO> list = produtoService.findAllProdutos();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> getProdutoById(@PathVariable Long id){

        ProdutoResponseDTO produtoDTO = produtoService.findProdutoById(id);

        return ResponseEntity.ok(produtoDTO);
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> createProduto(@RequestBody @Valid ProdutoRequestDTO requestDTO){

        ProdutoResponseDTO produtoDTO= produtoService.saveProduto(requestDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(produtoDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(produtoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> updateProduto(@PathVariable Long id,
                                                            @RequestBody @Valid ProdutoRequestDTO requestDTO){

        ProdutoResponseDTO produtoDTO = produtoService.updateProduto(id, requestDTO);

        return ResponseEntity.ok(produtoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id){

        produtoService.deleteProdutoById(id);

        return ResponseEntity.noContent().build();
    }



}
