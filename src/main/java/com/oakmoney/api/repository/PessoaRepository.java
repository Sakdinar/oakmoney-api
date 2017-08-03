package com.oakmoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oakmoney.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
