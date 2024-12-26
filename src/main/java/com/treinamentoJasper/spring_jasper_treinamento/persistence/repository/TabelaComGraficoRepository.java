package com.treinamentoJasper.spring_jasper_treinamento.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.treinamentoJasper.spring_jasper_treinamento.persistence.model.ProjetoPrincipal;

@RepositoryRestResource
public interface TabelaComGraficoRepository extends JpaRepository<ProjetoPrincipal, Integer>{

	@Query("SELECT p FROM ProjetoPrincipal p WHERE p.uf = :uf AND p.situacao = :situacao")
	List<ProjetoPrincipal> findAllByUf_Situacao(String uf, String situacao);

	@Query("SELECT p FROM ProjetoPrincipal p WHERE p.uf = :uf")
	List<ProjetoPrincipal> findAllByUf(String uf);

	@Query("SELECT p FROM ProjetoPrincipal p WHERE p.situacao = :situacao")
	List<ProjetoPrincipal> findAllBySituacao(String situacao);

}
