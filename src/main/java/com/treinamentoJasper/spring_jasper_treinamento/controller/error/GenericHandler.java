package com.treinamentoJasper.spring_jasper_treinamento.controller.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.treinamentoJasper.spring_jasper_treinamento.exception.GenericException;


@RestControllerAdvice
public class GenericHandler {
	private Logger logger = LoggerFactory.getLogger(GenericHandler.class);
	
	/**
	 * Método interceptador global da exception {@link GenericException}.
	 *
	 * 
	 * @author Núcleo de Implantação da Fábrica de Software - CASNAV, 2021
	 * @param exception uma {@link Exception}
	 * @return resposta HTTP relacionada
	 */
	@ExceptionHandler(GenericException.class)
	public ResponseEntity<String> handle(GenericException exception) {
		logger.error("Motivo da falha: {}", exception);

		String msg = "";
		if (exception.getLocalizedMessage() != null)
			msg = "\"" + exception.getLocalizedMessage() + "\"";

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.contentType(MediaType.APPLICATION_JSON)
				.body(msg);
	}

}
