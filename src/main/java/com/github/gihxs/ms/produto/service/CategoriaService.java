package com.github.gihxs.ms.produto.service;

import com.github.gihxs.ms.produto.dto.CategoriaDTO;
import com.github.gihxs.ms.produto.entities.Categoria;
import com.github.gihxs.ms.produto.exceptions.ResourceNotFoundException;
import com.github.gihxs.ms.produto.repositories.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<CategoriaDTO> findAllCategorias(){
        return categoriaRepository.findAll().stream().map(CategoriaDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoriaDTO findCategoriaById(Long id){
        Categoria categoria = categoriaRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id));

        return new CategoriaDTO(categoria);
    }

    @Transactional
    public CategoriaDTO saveCategoria(CategoriaDTO inputDTO){
        Categoria categoria = new Categoria();
        copyDtoToCategoria(inputDTO, categoria);
        categoria = categoriaRepository.save(categoria);
        return new CategoriaDTO(categoria);
    }

    public void copyDtoToCategoria (CategoriaDTO inputDTO, Categoria categoria){
        categoria.setNome(inputDTO.getNome());
    }
    @Transactional
    public CategoriaDTO updateCategoria(Long id, CategoriaDTO inputDTO){
        try{
            Categoria categoria = categoriaRepository.getReferenceById(id);
            copyDtoToCategoria(inputDTO, categoria);
            return new CategoriaDTO(categoria);
        } catch (EntityNotFoundException ex){
            throw new ResourceNotFoundException("Recurso não encontrado. Id: " + id);
        }
    }
}

