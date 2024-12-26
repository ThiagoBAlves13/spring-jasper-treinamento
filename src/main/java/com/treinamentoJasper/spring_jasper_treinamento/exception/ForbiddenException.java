package com.treinamentoJasper.spring_jasper_treinamento.exception;
import org.springframework.security.access.AccessDeniedException;

public class ForbiddenException extends AccessDeniedException{

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Construtor para capturar e tratar entidade não econtrada repassando para o construtor pai {@link RuntimeException}
	 * 
	 * @param entity {@link String}
	 */
	public ForbiddenException(String msg) {
		super(msg);
	}
}
