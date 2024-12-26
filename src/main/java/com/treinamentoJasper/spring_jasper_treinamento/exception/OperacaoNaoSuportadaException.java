package com.treinamentoJasper.spring_jasper_treinamento.exception;

public class OperacaoNaoSuportadaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Construtor para capturar e tratar entidade não encontrada repassando para o construtor pai {@link RuntimeException}
	 * 
	 * @param s {@link String}
	 */
	
    public OperacaoNaoSuportadaException(String s) {
        super(s);
    }

    /**
     * Construtor para capturar e tratar entidade não encontrada repassando para o construtor pai
     */
    
    public OperacaoNaoSuportadaException() {
    }
}