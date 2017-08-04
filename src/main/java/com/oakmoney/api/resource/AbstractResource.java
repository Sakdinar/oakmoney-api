package com.oakmoney.api.resource;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.oakmoney.api.model.AbstractModel;

public abstract class AbstractResource {

	@Autowired
	private ApplicationEventPublisher publisher;
	
	public URI getURI(AbstractModel abstractModel) {
		return ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{codigo}").buildAndExpand(abstractModel.getCodigo()).toUri();
	}

	public ApplicationEventPublisher getPublisher() {
		return publisher;
	}

}
