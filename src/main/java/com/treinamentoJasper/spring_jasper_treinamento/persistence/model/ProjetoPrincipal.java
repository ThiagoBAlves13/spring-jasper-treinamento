package com.treinamentoJasper.spring_jasper_treinamento.persistence.model;

import static com.treinamentoJasper.spring_jasper_treinamento.ServletInitializer.PENDENTE;
import static com.treinamentoJasper.spring_jasper_treinamento.ServletInitializer.RECEBIDO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name="projeto_principal")
public class ProjetoPrincipal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_projeto")
	private Integer id;

	@Column(name="projeto")
	private String projeto;
	
	@Column(name="data_inicio")
	private LocalDate inicio;

	@Column(name="data_termino")
	private LocalDate fim;
	
	@Column(name = "uf")
	private String uf;
	
	@Column(name = "situacao")
	private String situacao;
	
	@Column(name = "origem_agregada")
	private String origemAgregada;
	
	@Column(name = "planejado")
	private BigDecimal planejado;
	
	@Column(name = "recebido")
	private BigDecimal recebido;
	
	@Column(name = "pendente")
	private BigDecimal pendente;
	
	@Transient
	private String statusRecurso;
	
	public String getStatusRecurso() {
		if((getPlanejado() != null) && (getRecebido() != null) && (getPendente() != null)) {
			if ((getRecebido().compareTo(getPlanejado()) < 0)&&(getPendente().compareTo(BigDecimal.ZERO) > 0))
					return PENDENTE;
			else if((getRecebido().compareTo(getPlanejado()) >= 0) || (getPendente().compareTo(BigDecimal.ZERO) == 0))
					return RECEBIDO;
		} 
		return null;
	}

}