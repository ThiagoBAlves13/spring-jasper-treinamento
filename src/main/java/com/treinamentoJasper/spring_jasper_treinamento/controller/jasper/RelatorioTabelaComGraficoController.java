package com.treinamentoJasper.spring_jasper_treinamento.controller.jasper;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.treinamentoJasper.spring_jasper_treinamento.ServletInitializer.Tipos;
import com.treinamentoJasper.spring_jasper_treinamento.literal.APIBasePaths;
import com.treinamentoJasper.spring_jasper_treinamento.literal.SwaggerDescription;
import com.treinamentoJasper.spring_jasper_treinamento.literal.SwaggerSummaries;
import com.treinamentoJasper.spring_jasper_treinamento.service.TabelaComGraficoService;
import com.treinamentoJasper.spring_jasper_treinamento.service.jasper.JasperService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@RestController
@RequestMapping(APIBasePaths.ROOT)
@Tag(name = "Relatórios")
public class RelatorioTabelaComGraficoController {
	
	@Autowired
	TabelaComGraficoService tabelaGraficoService;
	
	@Autowired
	JasperService jasperService;
	
	/**
	 * 
	 * GetMapping padrão para gerar um relatório com gráficos.
	 * 
	 * @param uf
	 * @param situacao
	 * @param tipo
	 * @param response
	 * @throws JRException
	 * @throws IOException
	 */
	@Operation(summary = SwaggerSummaries.SUM_003, description = SwaggerDescription.DESC_003)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = SwaggerDescription.COD_200),
			@ApiResponse(responseCode = "404", description = SwaggerDescription.COD_404) })
	@GetMapping(APIBasePaths.RELATORIOS + APIBasePaths.TABELA_GRAFICO)
	public void gerarRelatorioGrafico( 
			@RequestParam(name = "uf",required = false) String uf, @RequestParam(name = "situacao",required = false) String situacao,
			@RequestParam(name = "tipo",required = false, defaultValue = "pdf") @Parameter(description = "Tipo do arquivo") Tipos tipo,
			HttpServletResponse response) throws JRException, IOException{
		
		
		HashMap<String, Object> resultado = tabelaGraficoService.gerarRelatorioProjetosPorCriterios(uf, situacao);
		HashMap<String, Object> parametros = (HashMap<String, Object>) resultado.get("parametros");
		JRBeanCollectionDataSource dataSource = (JRBeanCollectionDataSource) resultado.get("dataSource");
		jasperService.gerandoRelatorio("relatorioTabelaComGrafico", parametros, dataSource, tipo, response);

	}
}
