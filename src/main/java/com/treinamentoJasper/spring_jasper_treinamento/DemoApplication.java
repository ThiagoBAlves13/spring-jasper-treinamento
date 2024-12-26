package com.treinamentoJasper.spring_jasper_treinamento;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.scheduling.annotation.EnableAsync;

import com.treinamentoJasper.spring_jasper_treinamento.literal.APIBasePaths;
import com.treinamentoJasper.spring_jasper_treinamento.service.helper.DataHoraHelper;

import jakarta.annotation.PostConstruct;

@EnableAsync
@SpringBootApplication
public class DemoApplication {
	private Logger logger = LoggerFactory.getLogger(DemoApplication.class);
	
	public static Map<String, Integer> RESTRICOES_CORES = new HashMap<>();
	
	public static void main(String[] args) {
		System.setProperty("spring.data.rest.base-path", APIBasePaths.ROOT);
		SpringApplication.run(DemoApplication.class, args);
			
		RESTRICOES_CORES.put("Férias", 1);
		RESTRICOES_CORES.put("Afastado Temporariamente - Restrição Médica", 2);
		RESTRICOES_CORES.put("Afastado Temporariamente - A pedido",3);
		RESTRICOES_CORES.put("Afastado Temporariamente - Punição", 4);
		RESTRICOES_CORES.put("Afastado Temporariamente - Não cumpriu o mínimo", 5);
		RESTRICOES_CORES.put("Afastado Definitivamente - A pedido", 6);
		RESTRICOES_CORES.put("Afastado Definitivamente - Falecimento", 7);
		RESTRICOES_CORES.put("Afastado Definitivamente - Restrição Médica", 1001);
		RESTRICOES_CORES.put("Afastado Definitivamente - Punição", 1002);
		RESTRICOES_CORES.put("Afastado Definitivamente - Sem exercício por mais de 24 meses", 1003);
		RESTRICOES_CORES.put("Afastado Temporariamente - Falta de Atualização", 1004);
		
	}

	@Bean
	ProjectionFactory projectionFactory() {
		return new SpelAwareProxyProjectionFactory();
	}

	@PostConstruct
	void started() {
		Locale.setDefault(DataHoraHelper.BRASIL);
		TimeZone.setDefault(TimeZone.getTimeZone(DataHoraHelper.ZONE_ID));
	}

}
