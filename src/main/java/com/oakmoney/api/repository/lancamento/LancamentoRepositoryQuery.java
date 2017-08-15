package com.oakmoney.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oakmoney.api.model.Lancamento;
import com.oakmoney.api.repository.filter.LancamentoFilter;
import com.oakmoney.api.repository.projection.ResumoLancamento;

public interface LancamentoRepositoryQuery {

	public Page<Lancamento> findByFilter(LancamentoFilter filter, Pageable pageable);
	public Page<ResumoLancamento> resumeByFilter(LancamentoFilter lancamentoFilter, Pageable pageable);
	
}
