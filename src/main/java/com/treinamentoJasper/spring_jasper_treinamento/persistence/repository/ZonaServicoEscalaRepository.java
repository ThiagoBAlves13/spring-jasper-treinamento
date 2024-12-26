package com.treinamentoJasper.spring_jasper_treinamento.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.treinamentoJasper.spring_jasper_treinamento.persistence.model.ZonaServicoEscala;

@RepositoryRestResource
public interface ZonaServicoEscalaRepository extends JpaRepository<ZonaServicoEscala, Integer> {

}
