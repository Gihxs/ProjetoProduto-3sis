package com.github.gihxs.ms.produto.service;

import com.github.gihxs.ms.produto.dto.CategoriaRequestDTO;
import com.github.gihxs.ms.produto.dto.CategoriaResponseDTO;
import com.github.gihxs.ms.produto.entities.Categoria;
import com.github.gihxs.ms.produto.exceptions.DatabaseException;
import com.github.gihxs.ms.produto.exceptions.ResourceNotFoundException;
import com.github.gihxs.ms.produto.repositories.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> findAllCategorias(){
        return categoriaRepository.findAll().stream().map(CategoriaResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO findCategoriaById(Long id){
        Categoria categoria = categoriaRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id));

        return new CategoriaResponseDTO(categoria);
    }

    @Transactional
    public CategoriaResponseDTO saveCategoria(CategoriaRequestDTO inputDTO){
        Categoria categoria = new Categoria();
        copyDtoToCategoria(inputDTO, categoria);
        categoria = categoriaRepository.save(categoria);
        return new CategoriaResponseDTO(categoria);
    }

    public void copyDtoToCategoria (CategoriaRequestDTO inputDTO, Categoria categoria){
        categoria.setNome(inputDTO.getNome());
    }
    @Transactional
    public CategoriaResponseDTO updateCategoria(Long id, CategoriaRequestDTO inputDTO){
        try{
            Categoria categoria = categoriaRepository.getReferenceById(id);
            copyDtoToCategoria(inputDTO, categoria);
            return new CategoriaResponseDTO(categoria);
        } catch (EntityNotFoundException ex){
            throw new ResourceNotFoundException("Recurso não encontrado. Id: " + id);
        }
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteCategoriaById(Long id){
         if(!categoriaRepository.existsById(id)){
             throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
         }
         try {
             categoriaRepository.deleteById(id);
         } catch (DataIntegrityViolationException e){
             throw new DatabaseException("Não foi possível excluir a categoria. Existem produtos associados a ela.");
         }
    }
}

