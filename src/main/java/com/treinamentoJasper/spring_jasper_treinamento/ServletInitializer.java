package com.treinamentoJasper.spring_jasper_treinamento;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {	
		return application.sources(DemoApplication.class);
	}
	
	public enum Tipos {
		pdf, xlsx, ods, odt, docx, html
	}
	
	public static final String RELATORIO_FORMATO_PDF = "pdf";
	public static final String RELATORIO_FORMATO_HTML = "html";
	public static final String RELATORIO_FORMATO_DOCX = "docx";
	public static final String RELATORIO_FORMATO_ODT = "odt";
	public static final String RELATORIO_FORMATO_ODS = "ods";
	public static final String RELATORIO_FORMATO_XLSX = "xlsx";
	
	public static String ESCALA_RATIFICADA = "RT";
	public static String ESCALA_RATIFICADA_RESTRICOES = "RR";
	public static List<String> ESCALAS_RATIFICADAS = Arrays.asList(ESCALA_RATIFICADA,
			   ESCALA_RATIFICADA_RESTRICOES);
	
	public static final String PENDENTE = "Pendente";
	public static final String RECEBIDO = "Recebido";
	
	public static final String ORIGEM_AGREGADA_IMPORTADA = "Importada";
	public static final String ORIGEM_AGREGADA_NACIONAL = "Nacional";
	public static final String ORIGEM_AGREGADA_NAO_DEFINIDO = "Não definido";
	
	public static final String CURSO_JASPER = "JasperReport";
	public static final String CURSO_ESPECIFICATION = "Especification";
	
}
