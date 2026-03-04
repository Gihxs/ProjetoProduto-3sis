package com.github.gihxs.ms.produto.repositories;

import com.github.gihxs.ms.produto.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
