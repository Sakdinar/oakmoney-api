package com.oakmoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oakmoney.api.model.Lancamento;
import com.oakmoney.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {
	
}
