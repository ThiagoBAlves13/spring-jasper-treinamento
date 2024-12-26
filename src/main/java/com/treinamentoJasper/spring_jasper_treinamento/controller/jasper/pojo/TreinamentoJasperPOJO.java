package com.treinamentoJasper.spring_jasper_treinamento.controller.jasper.pojo;

import java.util.List;

import lombok.Data;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Data
public class TreinamentoJasperPOJO {
	
	String curso;
	List<String> nomes;
	JRBeanCollectionDataSource jrNomes;

}
