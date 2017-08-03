package com.oakmoney.api.resource;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oakmoney.api.model.Pessoa;
import com.oakmoney.api.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource extends AbstractResource {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@GetMapping
	public ResponseEntity<List<Pessoa>> findAll() {
		List<Pessoa> pessoas = pessoaRepository.findAll();
		return null != pessoas && !pessoas.isEmpty() ? ResponseEntity.ok(pessoas) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> findOne(@PathVariable Long codigo) {
		Pessoa pessoa = pessoaRepository.findOne(codigo);
		return null != pessoa ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> novaPessoa(@Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		URI uri = getURI(pessoaSalva);
		return ResponseEntity.created(uri).body(pessoaSalva);
	}
	
}
