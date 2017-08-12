package com.oakmoney.api.repository.lancamento;

import java.util.List;

import com.oakmoney.api.model.Lancamento;
import com.oakmoney.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public List<Lancamento> filtrar(LancamentoFilter filter);
	
}
