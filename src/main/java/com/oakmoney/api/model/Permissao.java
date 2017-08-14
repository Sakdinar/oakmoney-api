package com.oakmoney.api.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "permissao")
public class Permissao extends AbstractModel {

	private static final long serialVersionUID = -3807937451786387401L;
	
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
