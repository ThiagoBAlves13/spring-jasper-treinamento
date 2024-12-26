package com.treinamentoJasper.spring_jasper_treinamento.exception;

import com.treinamentoJasper.spring_jasper_treinamento.literal.SystemMsgs;

public class RegistroDuplicadoException
	extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Construtor para capturar e tratar Elemento Duplicado já cadastrado repassando para o construtor pai {@link RuntimeException}
	 * 
	 * @param elementoDuplicado {@link String}
	 */
	
	public RegistroDuplicadoException(String elementoDuplicado) {
      super(elementoDuplicado+SystemMsgs.MSG_EXISTE_SISTEMA);
		
	}
	
	/**
	 * 
	 * Construtor para capturar e tratar Registro Duplicado repassando para o construtor pai {@link RuntimeException}
	 * 
	 */
	
	public RegistroDuplicadoException() {
	      super(SystemMsgs.DUPLICADO);
		}
  
}
