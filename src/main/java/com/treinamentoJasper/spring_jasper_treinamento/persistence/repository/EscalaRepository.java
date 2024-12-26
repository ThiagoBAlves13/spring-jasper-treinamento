package com.treinamentoJasper.spring_jasper_treinamento.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.treinamentoJasper.spring_jasper_treinamento.persistence.model.Escala;
import com.treinamentoJasper.spring_jasper_treinamento.persistence.model.ZonaServicoEscala;

@RepositoryRestResource
public interface EscalaRepository extends JpaRepository<Escala, Integer>{

	 @Query("SELECT e FROM Escala e " +
	            "WHERE e.situacao IN :ratificadas " +
	            "AND e.zs = :zona " +
	            "AND e.ano = :ano " + 
	            "AND e.mes = :mes ")
	Escala findByZsAndAnoAndMes(ZonaServicoEscala zona, Integer ano, short mes, List<String> ratificadas);

}
