package com.treinamentoJasper.spring_jasper_treinamento.controller.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.treinamentoJasper.spring_jasper_treinamento.exception.BadGatewayException;

@RestControllerAdvice
public class BadGatewayHandler {
	private Logger logger = LoggerFactory.getLogger(BadGatewayHandler.class);

	/**
	 * Método interceptador global da exception {@link BadGatewayException}.
	 *
	 * @param exception uma exception {@link BadGatewayException}
	 * @return resposta HTTP relacionada
	 */
	@ExceptionHandler(BadGatewayException.class)
	public ResponseEntity<String> handle(BadGatewayException exception) {
		logger.error("Motivo da falha: {}", exception);
		
		String msg ="";

		if (exception.getMessage() != null)
			msg = "\""+exception.getMessage()+"\"";
		
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
				.contentType(MediaType.APPLICATION_JSON)
				.body(msg);
	}

}
