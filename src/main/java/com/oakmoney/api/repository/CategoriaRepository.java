package com.oakmoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oakmoney.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
