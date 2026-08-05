package com.github.gihxs.ms.produto.controller;

import com.github.gihxs.ms.produto.dto.CategoriaRequestDTO;
import com.github.gihxs.ms.produto.dto.CategoriaResponseDTO;
import com.github.gihxs.ms.produto.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> getAllCategorias() {

        List<CategoriaResponseDTO> categorias = categoriaService.findAllCategorias();

        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> getCategoriaById(@PathVariable Long id) {

        CategoriaResponseDTO categoriaDto = categoriaService.findCategoriaById(id);
        return ResponseEntity.ok(categoriaDto);

    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> createCategoria(@RequestBody @Valid CategoriaRequestDTO inputDto) {

        CategoriaResponseDTO categoriaResponseDto = categoriaService.saveCategoria(inputDto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(categoriaResponseDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(categoriaResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> updateCategoria(@PathVariable Long id,
                                                                @Valid @RequestBody CategoriaRequestDTO inputDto) {

        CategoriaResponseDTO categoriaResponseDto = categoriaService.updateCategoria(id, inputDto);
        return ResponseEntity.ok(categoriaResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Valid> deleteCategoria(@PathVariable Long id) {

        categoriaService.deleteCategoriaById(id);

        return ResponseEntity.noContent().build();
    }
}

