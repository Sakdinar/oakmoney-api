package com.oakmoney.api.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "usuario")
public class Usuario extends AbstractModel {

	private static final long serialVersionUID = 1019278027700504878L;
	
	@NotBlank
	@Size(min=3, max=50)
	private String nome;
	@NotBlank
	@Size(min=5, max=50)
	private String email;
	@NotBlank
	@Size(min=6, max=150)
	private String senha;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="usuario_permissao", 
		joinColumns = @JoinColumn(name = "codigo_usuario"), 
		inverseJoinColumns = @JoinColumn(name ="codigo_permissao"))
	private List<Permissao> permissoes;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}
	
	
	
}
