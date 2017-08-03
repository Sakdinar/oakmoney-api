package com.oakmoney.api.resource;

import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.oakmoney.api.model.AbstractModel;

public abstract class AbstractResource {

	public URI getURI(AbstractModel abstractModel) {
		return ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{codigo}").buildAndExpand(abstractModel.getCodigo()).toUri();
	}
	
}
