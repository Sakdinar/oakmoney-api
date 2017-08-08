package com.oakmoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oakmoney.api.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
