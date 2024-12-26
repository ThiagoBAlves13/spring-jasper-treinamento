package com.treinamentoJasper.spring_jasper_treinamento.service;

import static com.treinamentoJasper.spring_jasper_treinamento.ServletInitializer.ORIGEM_AGREGADA_IMPORTADA;
import static com.treinamentoJasper.spring_jasper_treinamento.ServletInitializer.ORIGEM_AGREGADA_NACIONAL;
import static com.treinamentoJasper.spring_jasper_treinamento.ServletInitializer.ORIGEM_AGREGADA_NAO_DEFINIDO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.treinamentoJasper.spring_jasper_treinamento.controller.jasper.pojo.ProjetoPojo;
import com.treinamentoJasper.spring_jasper_treinamento.persistence.model.ProjetoPrincipal;
import com.treinamentoJasper.spring_jasper_treinamento.persistence.repository.TabelaComGraficoRepository;
import com.treinamentoJasper.spring_jasper_treinamento.service.helper.BigDecimalPercentages;
import com.treinamentoJasper.spring_jasper_treinamento.service.helper.DataHoraHelper;
import com.treinamentoJasper.spring_jasper_treinamento.service.jasper.JasperService;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class TabelaComGraficoService {
	
	@Autowired
	JasperService jasperService;
	
	@Autowired
	DataHoraHelper dataHoraHelperService;
	
	@Autowired
	TabelaComGraficoRepository tabelaComGraficoRepository;

	public HashMap<String, Object> gerarRelatorioProjetosPorCriterios(String uf, String situacao) {
			
		HashMap<String, Object> parametros = new HashMap<>();
		List<ProjetoPrincipal> principaisProjetos = new ArrayList<>();
		List<ProjetoPojo> projetosPojo = new ArrayList<>();

		ClassPathResource classPathResource = new ClassPathResource("./images/imagem.jpeg");
        parametros.put("parLogo", classPathResource.getPath());
        
        if(uf != null && situacao != null)
        	principaisProjetos = tabelaComGraficoRepository.findAllByUf_Situacao(uf, situacao);
        else if (uf != null)
        	principaisProjetos = tabelaComGraficoRepository.findAllByUf(uf);
        else if (situacao != null)
        	principaisProjetos = tabelaComGraficoRepository.findAllBySituacao(situacao);
        else
        	principaisProjetos = tabelaComGraficoRepository.findAll();
        
		parametros.put("parSituacoes", situacao);
		parametros.put("parUF", uf);
				
		projetosPojo = this.preencheProjetosPojo(principaisProjetos, parametros);
				
		if(projetosPojo.isEmpty())
			parametros.put("parProjetosNaoEncontrado", true);
		else
			parametros.put("parProjetosNaoEncontrado", false);
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(projetosPojo);	
		
		return jasperService.dadosResultantes(dataSource, parametros);

	}

	/**
	 * 
	 * 
	 * 
	 * @param poseidonProjetos
	 * @return
	 */
	private List<ProjetoPojo> preencheProjetosPojo(List<ProjetoPrincipal> principaisProjetos, 
			Map<String, Object> parametros) {
		
		List<ProjetoPojo> projetosPojo = new ArrayList<>();
		Map<String, BigDecimal> somaPlanejadoPorUf = new HashMap<>();
        Map<String, BigDecimal> somaRecebidoPorUf = new HashMap<>();
        Map<String, BigDecimal> somaPendentePorUf = new HashMap<>();
		BigDecimal valorTotalPlanejadoAgregadoImportada = new BigDecimal(0);
		BigDecimal valorTotalPlanejadoAgregadoNacional = new BigDecimal(0);
		BigDecimal valorTotalPlanejadoAgregadoNaoDefinido = new BigDecimal(0);
		BigDecimal valorTotalRecebidoAgregadoImportada = new BigDecimal(0);
		BigDecimal valorTotalRecebidoAgregadoNacional = new BigDecimal(0);
		BigDecimal valorTotalRecebidoAgregadoNaoDefinido = new BigDecimal(0);
		BigDecimal valorTotalPendenteAgregadoImportada = new BigDecimal(0);
		BigDecimal valorTotalPendenteAgregadoNacional = new BigDecimal(0);
		BigDecimal valorTotalPendenteAgregadoNaoDefinido = new BigDecimal(0);
		
		for(ProjetoPrincipal pp : principaisProjetos) {
			
			ProjetoPojo projetoPojo = new ProjetoPojo();
			
			String uf = pp.getUf();
            somaPlanejadoPorUf.put(uf, somaPlanejadoPorUf.get(uf) != null ? (somaPlanejadoPorUf.get(uf).add(pp.getPlanejado() == null ? new BigDecimal(0) : pp.getPlanejado())) : pp.getPlanejado() == null ? new BigDecimal(0) : pp.getPlanejado());
            somaRecebidoPorUf.put(uf, somaRecebidoPorUf.get(uf) != null ? (somaRecebidoPorUf.get(uf).add(pp.getRecebido() == null ? new BigDecimal(0) : pp.getRecebido())) : pp.getRecebido() == null ? new BigDecimal(0) : pp.getRecebido());
            somaPendentePorUf.put(uf, somaPendentePorUf.get(uf) != null ? (somaPendentePorUf.get(uf).add(pp.getPendente() == null ? new BigDecimal(0) : pp.getPendente())) : pp.getPendente() == null ? new BigDecimal(0) : pp.getPendente());
			
			projetoPojo.setUf(pp.getUf().trim());
			projetoPojo.setProjeto(pp.getProjeto().trim());
			if(pp.getInicio() != null)
				projetoPojo.setDataInicio(dataHoraHelperService.dateFormat(pp.getInicio()));
			if(pp.getFim() != null)
				projetoPojo.setDataFim(dataHoraHelperService.dateFormat(pp.getFim()));
			projetoPojo.setSituacao(pp.getSituacao());
			projetoPojo.setOrigemAgregada(pp.getOrigemAgregada());
			projetoPojo.setStatusRecurso(pp.getStatusRecurso());
			projetoPojo.setPendente(pp.getPendente());
			projetoPojo.setPlanejado(pp.getPlanejado());
			projetoPojo.setRecebido(pp.getRecebido());

			if (pp.getOrigemAgregada().equalsIgnoreCase(ORIGEM_AGREGADA_IMPORTADA)) {
				valorTotalPlanejadoAgregadoImportada = valorTotalPlanejadoAgregadoImportada.add(pp.getPlanejado());
				valorTotalRecebidoAgregadoImportada = valorTotalRecebidoAgregadoImportada.add(pp.getRecebido());
				valorTotalPendenteAgregadoImportada = valorTotalPendenteAgregadoImportada
						.add(pp.getPlanejado().subtract(pp.getRecebido()));
			}
			if (pp.getOrigemAgregada().equalsIgnoreCase(ORIGEM_AGREGADA_NACIONAL)) {
				valorTotalPlanejadoAgregadoNacional = valorTotalPlanejadoAgregadoNacional.add(pp.getPlanejado());
				valorTotalRecebidoAgregadoNacional = valorTotalRecebidoAgregadoNacional.add(pp.getRecebido());
				valorTotalPendenteAgregadoNacional = valorTotalPendenteAgregadoNacional
						.add(pp.getPlanejado().subtract(pp.getRecebido()));
			}
			if (pp.getOrigemAgregada().equalsIgnoreCase(ORIGEM_AGREGADA_NAO_DEFINIDO)) {
				valorTotalPlanejadoAgregadoNaoDefinido = valorTotalPlanejadoAgregadoNaoDefinido.add(pp.getPlanejado());
				valorTotalRecebidoAgregadoNaoDefinido = valorTotalRecebidoAgregadoNaoDefinido.add(pp.getRecebido());
				valorTotalPendenteAgregadoNaoDefinido = valorTotalPendenteAgregadoNaoDefinido
						.add(pp.getPlanejado().subtract(pp.getRecebido()));
			}
			projetosPojo.add(projetoPojo);
		}
		
		parametros.put("totalPlanejadoImportada", valorTotalPlanejadoAgregadoImportada);
		parametros.put("totalRecebidoImportada", valorTotalRecebidoAgregadoImportada);
		parametros.put("totalPendenteImportada", valorTotalPendenteAgregadoImportada);
		
		parametros.put("totalPlanejadoNacional", valorTotalPlanejadoAgregadoNacional);
		parametros.put("totalRecebidoNacional", valorTotalRecebidoAgregadoNacional);
		parametros.put("totalPendenteNacional", valorTotalPendenteAgregadoNacional);
		
		parametros.put("totalPlanejadoNaoDefinico", valorTotalPlanejadoAgregadoNaoDefinido);
		parametros.put("totalRecebidoNaoDefinico", valorTotalRecebidoAgregadoNaoDefinido);
		parametros.put("totalPendenteNaoDefinico", valorTotalPendenteAgregadoNaoDefinido);
		
		parametros.put("agregadoImportada", ORIGEM_AGREGADA_IMPORTADA);
		parametros.put("agregadoNacional", ORIGEM_AGREGADA_NACIONAL);
		parametros.put("agregadoNaoDefinido", ORIGEM_AGREGADA_NAO_DEFINIDO);
		
		BigDecimal valorTotalPlanejado = valorTotalPlanejadoAgregadoImportada.add(valorTotalPlanejadoAgregadoNacional).add(valorTotalPlanejadoAgregadoNaoDefinido);
		BigDecimal valorTotalRecebido = valorTotalRecebidoAgregadoImportada.add(valorTotalRecebidoAgregadoNacional).add(valorTotalRecebidoAgregadoNaoDefinido);
		BigDecimal valorTotalPendente = valorTotalPendenteAgregadoImportada.add(valorTotalPendenteAgregadoNacional).add(valorTotalPendenteAgregadoNaoDefinido);
		
		BigDecimalPercentages percentualRecebido = new BigDecimalPercentages();
		BigDecimalPercentages percentualPendente = new BigDecimalPercentages();
		
		parametros.put("percentualRecebido", percentualRecebido.toPercentageOf(valorTotalRecebido, valorTotalPlanejado));
		parametros.put("percentualPendente", percentualPendente.toPercentageOf(valorTotalPendente, valorTotalPlanejado));
		parametros.put("parSomatorioPlanejado", somaPlanejadoPorUf);
		parametros.put("parSomatorioRecebido", somaRecebidoPorUf);
		parametros.put("parSomatorioPendente", somaPendentePorUf);
		
		return projetosPojo.stream().distinct().collect(Collectors.toList());
	}

}
