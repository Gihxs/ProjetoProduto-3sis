package com.github.gihxs.ms.produto.repositories;

import com.github.gihxs.ms.produto.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
