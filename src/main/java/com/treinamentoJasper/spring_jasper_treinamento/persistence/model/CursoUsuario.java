package com.treinamentoJasper.spring_jasper_treinamento.persistence.model;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "curso_usuario")
public class CursoUsuario implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CursoUsuarioPK id;


}
