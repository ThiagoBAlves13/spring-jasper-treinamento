package com.treinamentoJasper.spring_jasper_treinamento.service;

import static com.treinamentoJasper.spring_jasper_treinamento.DemoApplication.RESTRICOES_CORES;
import static com.treinamentoJasper.spring_jasper_treinamento.ServletInitializer.ESCALAS_RATIFICADAS;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treinamentoJasper.spring_jasper_treinamento.controller.jasper.pojo.CalendEscalaPOJO;
import com.treinamentoJasper.spring_jasper_treinamento.exception.NotFoundException;
import com.treinamentoJasper.spring_jasper_treinamento.persistence.model.Escala;
import com.treinamentoJasper.spring_jasper_treinamento.persistence.model.Escalacao;
import com.treinamentoJasper.spring_jasper_treinamento.persistence.model.ZonaServicoEscala;
import com.treinamentoJasper.spring_jasper_treinamento.persistence.repository.EscalaRepository;
import com.treinamentoJasper.spring_jasper_treinamento.persistence.repository.EscalacaoRepository;
import com.treinamentoJasper.spring_jasper_treinamento.persistence.repository.ZonaServicoEscalaRepository;
import com.treinamentoJasper.spring_jasper_treinamento.service.helper.DataHoraHelper;
import com.treinamentoJasper.spring_jasper_treinamento.service.jasper.JasperService;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class CalendarioEscalaService {

	private static final String RELNOME = "Escala de Rodízio por Região de Serviço";

    @Autowired
    ZonaServicoEscalaRepository zsRepository;

    @Autowired
    EscalaRepository escalaRepository;
    
    @Autowired
    EscalacaoRepository escalacaoRepository;

    @Autowired
    JasperService jasperService;
    
    @Autowired
    DataHoraHelper dataHoraHelper;

    /**
     * 
     * Método auxiliar para geração do relatório. A {@link String} recebida como parâmetro indicando um determinado local
     * como referência para gerar Escala de Serviço de determinada região.
     * 
     * @param local {@link String} Indica local desejado
     * @param mes {@link Integer} Indica o mês
     * @param ano {@link Integer} Indica o ano
     * @return {@link HashMap} dos dados resultantes.
     */
	public HashMap<String, Object> gerarRelatorio(String local, Integer mes, Integer ano) {
		
		HashMap<String, Object> dadosResultantes = new HashMap<>();
        HashMap<String, Object> parametros = new HashMap<>();
        List<CalendEscalaPOJO> globalPojos = new ArrayList<>();
        List<String> usuariosComTrigramas = new ArrayList<>();
        
        if(mes == null)
        	mes = LocalDate.now().getMonthValue();
        if(ano == null)
        	ano = LocalDate.now().getYear();
        
        Integer idZS = Integer.parseInt(local);    
        ZonaServicoEscala zona = zsRepository.findById(idZS).get();
        
        Escala escala = escalaRepository.findByZsAndAnoAndMes(zona, ano, mes.shortValue(), ESCALAS_RATIFICADAS);
        if(escala == null)
        	throw new NotFoundException("Escala");
        List<Escalacao> escalacoes = escalacaoRepository.findAtivas(escala);
        
        Locale localDate = new Locale("pt", "BR");
                
        for(Escalacao escalacao : escalacoes) {
        	LocalDate data = LocalDate.of(ano, mes, escalacao.getNrDia());
        	CalendEscalaPOJO rel008Pojo = new CalendEscalaPOJO();
        	String praticoComTrigrama = new String();
        	
        	rel008Pojo.setAno(ano.toString());
        	rel008Pojo.setMes(data.getMonth().getDisplayName(TextStyle.SHORT, localDate).toUpperCase());
        	rel008Pojo.setDia(String.format("%02d",escalacao.getNrDia()));
        	rel008Pojo.setSemana(data.getDayOfWeek().getDisplayName(TextStyle.SHORT, localDate).toUpperCase());
        	rel008Pojo.setTipoEscalacao(escalacao.getDsTipoEscalacao());
        	rel008Pojo.setTrigrama(escalacao.getUsuario().getTrigrama());
        	
        	praticoComTrigrama = escalacao.getUsuario().getTrigrama() + " - " + escalacao.getUsuario().getNome();
        	usuariosComTrigramas.add(praticoComTrigrama);
        	
        	if(escalacao.getUsuario().getRestricao() != null)
        		rel008Pojo.setRestricao(RESTRICOES_CORES.get(escalacao.getUsuario().getRestricao()).toString());
        	globalPojos.add(rel008Pojo);
        	
        }
        
        globalPojos = this.verificaMesCompleto(globalPojos, ano, mes);
        globalPojos = globalPojos.stream().sorted((Comparator.comparing(CalendEscalaPOJO :: getTrigrama)))
        		.sorted((Comparator.comparing(CalendEscalaPOJO :: getDia)))
        		.sorted((Comparator.comparing(CalendEscalaPOJO :: getTipoEscalacao)))
        		.collect(Collectors.toList());
        
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(globalPojos);
        JasperReport trigramaNomeUsuarioSubReport = jasperService.compileReport("relatorioTrigramaUsuarioSubReport");
        
        parametros.put("usuariosComTrigramas", usuariosComTrigramas.stream().sorted().distinct().collect(Collectors.toList()));
        parametros.put("jrUsuariosComTrigramas", new JRBeanCollectionDataSource(usuariosComTrigramas.stream().sorted().distinct().collect(Collectors.toList())));
        parametros.put("parTrigramaNomeUsuariosSubReport", trigramaNomeUsuarioSubReport);
        parametros.put("parRelatorio", RELNOME);
        parametros.put("parRegiao", zona.getCodigo() + ": " + zona.getDescricao() + ", " + zona.getUf());
        parametros.put("parUltimoDiaMes", this.obterUltimoDiaMes(ano, mes));
        parametros.put("parMes", dataHoraHelper.mesCompleto(ano, mes));
        dadosResultantes.put("parametros", parametros);
        dadosResultantes.put("dataSource", dataSource);

        return dadosResultantes;
	}

	/**
	 * 
	 * Método verifica se algum dia no mês não houve escalação e complementa com "-" para indicar sem escalação no dia.
	 * 
	 * @param globalPojos {@link List}
	 * @param ano
	 * @param mes
	 * @return {@link List}
	 */
	private List<CalendEscalaPOJO> verificaMesCompleto(List<CalendEscalaPOJO> globalPojos, Integer ano, Integer mes) {
		
		List<Integer> diasEscalaNormal = new ArrayList<>();
		List<Integer> diasEscalaStandby = new ArrayList<>();
		globalPojos.forEach(pojo -> {
			if(pojo.getTipoEscalacao().equals("Normal"))
				diasEscalaNormal.add(Integer.valueOf(pojo.getDia()));
			else
				diasEscalaStandby.add(Integer.valueOf(pojo.getDia()));
			});
		List<Integer> diasNaoRepetidosEscalaNormal = diasEscalaNormal.stream().sorted().distinct().collect(Collectors.toList());
		List<Integer> diasNaoRepetidosEscalaStandby = diasEscalaStandby.stream().sorted().distinct().collect(Collectors.toList());
		
		List<CalendEscalaPOJO>globalPojosEscalaNormal = this.completaDiaMesSemEscalacao(diasNaoRepetidosEscalaNormal, ano, mes, "Normal");
		List<CalendEscalaPOJO>globalPojosEscalaStandby =  this.completaDiaMesSemEscalacao(diasNaoRepetidosEscalaStandby, ano, mes, "Standby");
		
		globalPojos.addAll(globalPojosEscalaNormal);
		globalPojos.addAll(globalPojosEscalaStandby);
		
		return globalPojos;
	}

	/**
	 * 
	 * 
	 * 
	 * @param diasNaoRepetidosEscala
	 * @param ano
	 * @param mes
	 * @param tipoEscalacao
	 * @return
	 */
	private List<CalendEscalaPOJO> completaDiaMesSemEscalacao(List<Integer> diasNaoRepetidosEscala, Integer ano, 
			Integer mes, String tipoEscalacao) {

		int ultimoDiaMes = this.obterUltimoDiaMes(ano, mes);
		Locale local = new Locale("pt", "BR");
		List<CalendEscalaPOJO> globalPojos = new ArrayList<>();
		
		if(diasNaoRepetidosEscala.size() != ultimoDiaMes) {
			
			for(int i = 1; i <= ultimoDiaMes; i++) {
				Integer dia = i;
				Boolean existe = false;
				for(Integer d : diasNaoRepetidosEscala) {					
					if(dia == d)
						existe = true;
				};
				if(!existe) {
					LocalDate dataEscala = LocalDate.of(ano, mes, dia);
		        	CalendEscalaPOJO caledarioEscalaPojo = new CalendEscalaPOJO();
		        	
		        	caledarioEscalaPojo.setAno(ano.toString());
		        	caledarioEscalaPojo.setMes(dataEscala.getMonth().getDisplayName(TextStyle.SHORT, local).toUpperCase());
		        	caledarioEscalaPojo.setDia(String.format("%02d", dia));
		        	caledarioEscalaPojo.setSemana(dataEscala.getDayOfWeek().getDisplayName(TextStyle.SHORT, local).toUpperCase());
		        	caledarioEscalaPojo.setTipoEscalacao(tipoEscalacao);
		        	caledarioEscalaPojo.setTrigrama(" --- ");
		        	globalPojos.add(caledarioEscalaPojo);
				}
			}
		}
		return globalPojos;
	}

	/**
	 * 
	 * Método que obtém o último dia do Mês;
	 * 
	 * @param ano
	 * @param mes
	 * @return int
	 */
	private int obterUltimoDiaMes(Integer ano, Integer mes) {
		
		Calendar data = Calendar.getInstance();
		data.set(ano, mes, 00);
		int ultimoDiaMes = data.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		return ultimoDiaMes;
	}
}

