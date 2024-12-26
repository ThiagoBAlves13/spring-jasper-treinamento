package com.treinamentoJasper.spring_jasper_treinamento.controller.error;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.treinamentoJasper.spring_jasper_treinamento.exception.OperacaoNaoSuportadaException;
import com.treinamentoJasper.spring_jasper_treinamento.exception.ParametrosInvalidosException;
import com.treinamentoJasper.spring_jasper_treinamento.exception.RegistroDuplicadoException;

import jakarta.validation.ConstraintViolation;

@RestControllerAdvice
public class UnprocessableEntityHandler {
	private Logger logger = LoggerFactory.getLogger(UnprocessableEntityHandler.class);
	
	@Autowired RegistroDuplicadoHandler rdHandler;
	
	/**
	 * Método interceptador global da exception {@link javax.validation.ConstraintViolationException}
	 *
	 * @param exception uma {@link javax.validation.ConstraintViolationException}
	 * @return resposta HTTP relacionada
	 */
	@ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
	public ResponseEntity<?> handle(jakarta.validation.ConstraintViolationException exception) {
		logger.error("Motivo da falha: {}", exception);
		
		Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
		
		if (constraintViolations == null) {
			String strBody = "";
			strBody += exception.getMessage();
			return ResponseEntity.unprocessableEntity().body("\""+strBody+"\"");
		}
					
		Map<String, List<String>> errMap = new HashMap<>();
		for (ConstraintViolation<?> c: constraintViolations) {
			String fieldName = c.getPropertyPath().toString();
			if(!errMap.containsKey(fieldName))
				errMap.put(fieldName, new ArrayList<>());
			errMap.get(fieldName).add(c.getMessage());
		}		
		return ResponseEntity.unprocessableEntity().body(errMap);
	}

	/**
	 * Método interceptador global da exception {@link org.hibernate.exception.ConstraintViolationException}
	 *
	 * @param exception uma {@link org.hibernate.exception.ConstraintViolationException}
	 * @return resposta HTTP relacionada
	 */
	@ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
	public ResponseEntity<?> handle(org.hibernate.exception.ConstraintViolationException exception) {
		logger.error("Motivo da falha: {}", exception);
		
		SQLException sqlException = exception.getSQLException();
		if (sqlException.getErrorCode() == 23505 || sqlException.getMessage().contains("duplica"))
			return rdHandler.handle(new RegistroDuplicadoException());

		return ResponseEntity.unprocessableEntity().body("");
	}
	
	/**
	 * 
	 * Método interceptador global da exception {@link ParametrosInvalidosException}.
	 * 
	 * @param exception uma {@link ParametrosInvalidosException}
	 * @return resposta HTTP relacionada
	 */
	
	@ExceptionHandler(ParametrosInvalidosException.class)
	public ResponseEntity<String> handle(ParametrosInvalidosException exception) {
		logger.error("Motivo da falha: {}", exception);

		String msg = "";
		if (exception.getLocalizedMessage() != null)
			msg = "\"" + exception.getLocalizedMessage() + "\"";

		return ResponseEntity.unprocessableEntity().body(msg);

	}
	
	/**
	 * 
	 * Método interceptador global da exception {@link OperacaoNaoSuportadaException}.
	 * 
	 * @param exception uma {@link OperacaoNaoSuportadaException}
	 * @return resposta HTTP relacionada
	 */
	
	@ExceptionHandler(OperacaoNaoSuportadaException.class)
	public ResponseEntity<String> handle(OperacaoNaoSuportadaException exception) {
		logger.error("Motivo da falha: {}", exception);

		String msg = "";
		if (exception.getLocalizedMessage() != null)
			msg = "\"" + exception.getLocalizedMessage() + "\"";

		return ResponseEntity.unprocessableEntity().body(msg);

	}
	
	/**
	 * 
	 * Método interceptador global da exception {@link OperacaoNaoSuportadaException}.
	 * 
	 * @param exception uma {@link OperacaoNaoSuportadaException}
	 * @return resposta HTTP relacionada
	 */
	
	@ExceptionHandler(UnrecognizedPropertyException.class)
	public ResponseEntity<String> handle(UnrecognizedPropertyException exception) {
		logger.error("Motivo da falha: {}", exception);

		String msg = "\"Falha ao processar requisição: campo inválido ou não permitido.\"";

		return ResponseEntity.unprocessableEntity().body(msg);

	}
	
	/**
	 * 
	 * Método interceptador global da exception {@link OperacaoNaoSuportadaException}.
	 * 
	 * @param exception uma {@link OperacaoNaoSuportadaException}
	 * @return resposta HTTP relacionada
	 */
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> handle(DataIntegrityViolationException exception) {
		logger.error("Motivo da falha: {}", exception);

		String msg = "\"Falha ao processar requisição: campo inválido ou não permitido.\"";

		return ResponseEntity.unprocessableEntity().body(msg);

	}
	
}
