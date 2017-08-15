package com.oakmoney.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oakmoney.api.model.Pessoa;
import com.oakmoney.api.repository.filter.PessoaFilter;

public interface PessoaRepositoryQuery {

	Page<Pessoa> findByFilter(PessoaFilter filter, Pageable pageable);
	
}
