package com.treinamentoJasper.spring_jasper_treinamento.service.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;


@Service
public class OAuthService {

	public String extrairNip() {
		Jwt principal = (Jwt) obterAutenticacao().getPrincipal();
		
		return principal.getClaimAsString("preferred_username");
    }

	private Authentication obterAutenticacao() {
    	return SecurityContextHolder.getContext().getAuthentication();
    }

	
}
