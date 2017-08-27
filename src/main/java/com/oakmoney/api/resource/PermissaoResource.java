package com.oakmoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oakmoney.api.event.RecursoCriadoEvent;
import com.oakmoney.api.model.Permissao;
import com.oakmoney.api.repository.PermissaoRepository;

@RequestMapping("/permissao")
@RestController
public class PermissaoResource extends AbstractResource {

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@GetMapping
	public ResponseEntity<List<Permissao>> findAll() {
		List<Permissao> permissoes = permissaoRepository.findAll();
		return null != permissoes && !permissoes.isEmpty() ? ResponseEntity.ok(permissoes) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{descricao}")
	public ResponseEntity<Permissao> findByDescricao(@PathVariable String descricao) {
		Permissao permissao = permissaoRepository.findByDescricao(descricao);
		return null != permissao ? ResponseEntity.ok(permissao) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Permissao> save(@RequestBody Permissao permissao, HttpServletResponse response) {
		Permissao permissaoSalva = permissaoRepository.save(permissao);
		getPublisher().publishEvent(new RecursoCriadoEvent(this, response, permissaoSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(permissaoSalva);
	}
	
}
