package com.treinamentoJasper.spring_jasper_treinamento.exception;

public class BadGatewayException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BadGatewayException() {
		super();
	}
	
	public BadGatewayException(String msg) {
		super(msg);
	}
}
