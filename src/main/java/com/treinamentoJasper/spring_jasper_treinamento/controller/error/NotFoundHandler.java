package com.treinamentoJasper.spring_jasper_treinamento.controller.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.treinamentoJasper.spring_jasper_treinamento.exception.NotFoundException;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestControllerAdvice
public class NotFoundHandler {
	private Logger logger = LoggerFactory.getLogger(NotFoundHandler.class);
	
	/**
	 * Método interceptador global da exception {@link NotFoundException}.
	 *
	 * @author Núcleo de Implantação da Fábrica de Software - CASNAV, 2021
	 * @param exception uma {@link Exception}
	 * @return resposta HTTP relacionada
	 */
	@ApiResponse(responseCode = "404",
			     description = "Recurso não encontrado.",
			     content = @Content(schema = @Schema(example = "Não foram encontrados recursos com o identificador informado.")))
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<String> handle(NotFoundException exception) {
		logger.error("Motivo da falha: {}", exception);

		String msg = "";
		if (exception.getLocalizedMessage() != null)
			msg = "\"" + exception.getLocalizedMessage() + "\"";

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.contentType(MediaType.APPLICATION_JSON)
				.body(msg);
	}

}

