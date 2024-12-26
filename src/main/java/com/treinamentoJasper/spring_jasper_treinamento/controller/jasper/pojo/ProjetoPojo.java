package com.treinamentoJasper.spring_jasper_treinamento.controller.jasper.pojo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProjetoPojo {
	
	String uf;
	String projeto;
	String dataInicio;
	String dataFim;
	String situacao;
	String origemAgregada;
	String statusRecurso;
	BigDecimal planejado;
	BigDecimal recebido;
	BigDecimal pendente;

}
