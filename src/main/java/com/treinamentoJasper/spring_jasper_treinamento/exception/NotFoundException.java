package com.treinamentoJasper.spring_jasper_treinamento.exception;

import com.treinamentoJasper.spring_jasper_treinamento.literal.SystemMsgs;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Construtor para capturar e tratar entidade não econtrada repassando para o construtor pai {@link RuntimeException}
	 * 
	 * @param entity {@link String}
	 */
	
	public NotFoundException(String entity) {
		super(entity+SystemMsgs.NAO_ENCONTRADO);
	}

}