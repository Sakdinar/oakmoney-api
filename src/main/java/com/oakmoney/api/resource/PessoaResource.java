package com.oakmoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oakmoney.api.event.RecursoCriadoEvent;
import com.oakmoney.api.model.Pessoa;
import com.oakmoney.api.repository.PessoaRepository;
import com.oakmoney.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource extends AbstractResource {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private PessoaService pessoaService;
	
	
	
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
	public ResponseEntity<Pessoa> novaPessoa(@Valid @RequestBody Pessoa pessoa, HttpServletResponse httpResponse) {
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		getPublisher().publishEvent(new RecursoCriadoEvent(this, httpResponse, pessoaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		pessoaRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa) {
		Pessoa pessaoSalva = pessoaService.atualizar(codigo, pessoa);
		return ResponseEntity.ok(pessaoSalva);
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		pessoaService.atualizarPropriedadeAtivo(codigo, ativo);
	}
	
}
