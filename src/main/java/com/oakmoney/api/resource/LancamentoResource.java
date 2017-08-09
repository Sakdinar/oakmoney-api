package com.oakmoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import com.oakmoney.api.model.Lancamento;
import com.oakmoney.api.repository.LancamentoRepository;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource extends AbstractResource {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@GetMapping
	public ResponseEntity<List<Lancamento>> buscarTodos() {
		List<Lancamento> lancamentos = lancamentoRepository.findAll();
		return null != lancamentos && !lancamentos.isEmpty() ? ResponseEntity.ok(lancamentos) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> obterPeloCodigo(@PathVariable Long codigo) {
		Lancamento lancamento = lancamentoRepository.findOne(codigo);
		return null != lancamento ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> novoLancamento(@Valid @RequestBody Lancamento lancamento, HttpServletResponse httpResponse) {
		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);
		getPublisher().publishEvent(new RecursoCriadoEvent(this, httpResponse, lancamento.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	
}
