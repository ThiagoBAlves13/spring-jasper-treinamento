package com.treinamentoJasper.spring_jasper_treinamento.controller.jasper.pojo;

import lombok.Data;

/**
 * Classe POJO que armazena {@link String} simples para a impressão do Jasper Reports
 * auxiliando em {@link REL008Service} - Relatório de Escala de Serviço por Zona de Praticagem
 *
 * @author Núcleo de Implantação da Fábrica de Software - CASNAV, 2021
 * 
 */

@Data
public class CalendEscalaPOJO {

	String mes;
	String ano;
	String semana;
	String dia;
	String trigrama;
	String tipoEscalacao;
	String restricao;

}

