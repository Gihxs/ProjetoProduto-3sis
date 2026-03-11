package com.github.gihxs.ms.produto.service;

import com.github.gihxs.ms.produto.dto.ProdutoDTO;
import com.github.gihxs.ms.produto.entities.Categoria;
import com.github.gihxs.ms.produto.entities.Produto;
import com.github.gihxs.ms.produto.exceptions.DatabaseException;
import com.github.gihxs.ms.produto.exceptions.ResourceNotFoundException;
import com.github.gihxs.ms.produto.repositories.CategoriaRepository;
import com.github.gihxs.ms.produto.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<ProdutoDTO> findAllProdutos(){

        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream().map(ProdutoDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ProdutoDTO findProdutoById(Long id){

        Produto produto = produtoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id)
        );

        return new ProdutoDTO(produto);
    }

    @Transactional
    public ProdutoDTO saveProduto(ProdutoDTO produtoDTO){

        try {
            Produto produto = new Produto();
            //metodo auxiliar para converter DTO para Entidade Produto
            copyDtoToProduto(produtoDTO, produto);
            produto = produtoRepository.save(produto);
            return new ProdutoDTO(produto);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não foi possível salvar Produto. Categoria inexistente ( ID: "
                    + produtoDTO.getCategoria().getId() + " )");
        }
    }

    @Transactional
    public ProdutoDTO updateProduto(Long id, ProdutoDTO produtoDTO){

        try {
            Produto produto = produtoRepository.getReferenceById(id);
            copyDtoToProduto(produtoDTO, produto);
            return new ProdutoDTO(produto);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }
    }

    @Transactional
    public void deleteProdutoById(Long id){

        if(!produtoRepository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

        produtoRepository.deleteById(id);
    }

    private void copyDtoToProduto(ProdutoDTO produtoDTO, Produto produto) {

        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setValor(produtoDTO.getValor());

        //objeto completo gerenciado
        Categoria categoria = categoriaRepository.getReferenceById(produtoDTO.getCategoria().getId());

        produto.setCategoria(categoria);
    }
}
