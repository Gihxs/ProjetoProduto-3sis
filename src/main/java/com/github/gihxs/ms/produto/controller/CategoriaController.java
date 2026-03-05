package com.github.gihxs.ms.produto.controller;

import com.github.gihxs.ms.produto.dto.CategoriaDTO;
import com.github.gihxs.ms.produto.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias(){
         List<CategoriaDTO> categorias = categoriaService.findAllCategorias();

         return ResponseEntity.ok(categorias);
    }

}
