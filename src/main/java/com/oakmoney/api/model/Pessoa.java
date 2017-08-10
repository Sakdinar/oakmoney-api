package com.oakmoney.api.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pessoa")
public class Pessoa extends AbstractModel {
	
	@NotNull
	private Boolean ativo;
	
	@NotBlank
	@Size(min = 3, max = 50)
	private String nome;
	
	@Embedded
	private Endereco endereco;

	@JsonIgnore
	@Transient
	public boolean isInativo() {
		return !getAtivo();
	}
	
	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
}
