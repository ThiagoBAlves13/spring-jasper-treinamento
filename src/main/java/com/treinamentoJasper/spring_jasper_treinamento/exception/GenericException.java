package com.treinamentoJasper.spring_jasper_treinamento.exception;

public class GenericException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Construtor para capturar e tratar GenericException repassando para o construtor pai {@link RuntimeException}
	 * 
	 * @param msg {@link String}
	 */
	
	public GenericException(String msg) {
		super(msg);
	}}
