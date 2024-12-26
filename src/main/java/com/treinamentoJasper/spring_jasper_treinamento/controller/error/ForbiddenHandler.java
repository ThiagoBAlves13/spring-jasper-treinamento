package com.treinamentoJasper.spring_jasper_treinamento.controller.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ForbiddenHandler {
	private Logger logger = LoggerFactory.getLogger(ForbiddenHandler.class);

	/**
	 * Método interceptador global da exception {@link AccessDeniedException}.
	 *
	 * @param exception uma {@link AccessDeniedException}
	 * @return resposta HTTP relacionada
	 */
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handle(AccessDeniedException exception) {
		logger.error("Motivo da falha: {}", exception);

		String msg ="";
		if (exception.getMessage() != null)
			msg = "\""+exception.getMessage()+"\"";
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.contentType(MediaType.APPLICATION_JSON)
				.body(msg);
	}
	
}