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
import org.springframework.web.bind.annotation.RestController;

import com.oakmoney.api.event.RecursoCriadoEvent;
import com.oakmoney.api.model.Usuario;
import com.oakmoney.api.repository.UsuarioRepository;
import com.oakmoney.api.service.UsuarioService;

@RequestMapping("/usuarios")
@RestController
public class UsuarioResource extends AbstractResource {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> findAll() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return null != usuarios && !usuarios.isEmpty() ? ResponseEntity.ok(usuarioRepository.findAll()) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Usuario> findById(@PathVariable Long codigo) {
		Usuario usuario = usuarioRepository.findOne(codigo);
		return null != usuario ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Usuario> save(@Valid @RequestBody Usuario usuario, HttpServletResponse httpResponse) {
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		getPublisher().publishEvent(new RecursoCriadoEvent(this, httpResponse, usuarioSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
	}
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<Usuario> delete(@PathVariable Long codigo) {
		usuarioRepository.delete(codigo);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/autalizarPermissoes/{codigo}")
	public ResponseEntity<Usuario> atualizarPermissoes(@PathVariable Long codigo, @RequestBody Usuario usuarioAtualizado) {
		Usuario usuario = usuarioService.atualizarPermissoes(codigo, usuarioAtualizado);
		return ResponseEntity.ok(usuario);
	}
	
}
