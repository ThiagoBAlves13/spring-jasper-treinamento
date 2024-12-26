package com.treinamentoJasper.spring_jasper_treinamento.persistence.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;


@Data
@Embeddable
@EqualsAndHashCode
@RequiredArgsConstructor
public class CursoUsuarioPK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "id_curso")
	private Curso curso;
	
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private Usuario usuario;
}
