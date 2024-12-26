package com.treinamentoJasper.spring_jasper_treinamento.persistence.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 
 * Classe Modelo da Entidade "Pratico".
 * 
 * @author Núcleo de Implantação da Fábrica de Software - CASNAV, 2021
 */

@Data
@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID_USUARIO")
	@JsonProperty(access = Access.READ_ONLY)
	private Integer id;

	@Size(max = 80)
	@Column(name = "DS_NOME")
	@JsonProperty(access = Access.READ_ONLY)
	private String nome;

	@Size(max = 3)
	@Column(name = "TRIGRAMA")
	@JsonProperty(access = Access.READ_ONLY)
	private String trigrama;

	@Column(name = "RESTRICAO")
	private String restricao;

}
