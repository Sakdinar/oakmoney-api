package com.oakmoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.oakmoney.api.model.Pessoa;
import com.oakmoney.api.repository.PessoaRepository;

@Component
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessaoSalva = pessoaRepository.findOne(codigo);
		if (null == pessaoSalva) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(pessoa, pessaoSalva, "codigo");
		return pessoaRepository.save(pessaoSalva);
	}
	
}
