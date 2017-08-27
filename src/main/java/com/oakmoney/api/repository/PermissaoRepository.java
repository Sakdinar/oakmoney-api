package com.oakmoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oakmoney.api.model.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
	
	public Permissao findByDescricao(String descricao);

}
