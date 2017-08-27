package com.oakmoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.oakmoney.api.model.Usuario;
import com.oakmoney.api.repository.UsuarioRepository;

@Component
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario atualizarPermissoes(Long codigo, Usuario usuarioAtualizado) {
		Usuario usuarioSalvo = usuarioRepository.findOne(codigo);
		if (null == usuarioSalvo) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(usuarioAtualizado, usuarioSalvo, "codigo", "email", "nome", "senha");
		return usuarioRepository.save(usuarioSalvo);
	}
	
}
