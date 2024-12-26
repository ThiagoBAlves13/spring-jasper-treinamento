package com.treinamentoJasper.spring_jasper_treinamento.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.treinamentoJasper.spring_jasper_treinamento.persistence.model.Escala;
import com.treinamentoJasper.spring_jasper_treinamento.persistence.model.Escalacao;

@RepositoryRestResource
public interface EscalacaoRepository extends JpaRepository<Escalacao, Integer>{

	@Query("SELECT e FROM Escalacao e " +
            "INNER JOIN Escala s " +
            "ON  s.id = e.escala.id " +
            "AND s = :escala " +
            "WHERE e.fgAtivo = True")
	List<Escalacao> findAtivas(Escala escala);

}
