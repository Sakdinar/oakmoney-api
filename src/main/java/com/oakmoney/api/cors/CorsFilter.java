package com.oakmoney.api.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.oakmoney.api.config.property.OakmoneyApiProperty;
import com.oakmoney.api.util.OakmoneyConstantsUtil;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

	@Autowired
	private OakmoneyApiProperty oakmoneyApiProperty;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		response.setHeader(OakmoneyConstantsUtil.ACCESS_CONTROL_ALLOW_ORIGIN, oakmoneyApiProperty.getOriginPermitida());
        response.setHeader(OakmoneyConstantsUtil.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
		
		if (HttpMethod.OPTIONS.name().equals(request.getMethod()) && oakmoneyApiProperty.getOriginPermitida().equals(request.getHeader("Origin"))) {
			response.setHeader(OakmoneyConstantsUtil.ACCESS_CONTROL_ALLOW_METHOD, OakmoneyConstantsUtil.ACCESS_CONTROL_ALLOW_METHOD_VALUES);
        	response.setHeader(OakmoneyConstantsUtil.ACCESS_CONTROL_ALLOW_HEADERS, OakmoneyConstantsUtil.ACCESS_CONTROL_ALLOW_HEADERS_VALUES);
        	response.setHeader(OakmoneyConstantsUtil.ACCESS_CONTROL_MAX_AGE, "3600");
			
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, resp);
		}
		
	}
	
	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}