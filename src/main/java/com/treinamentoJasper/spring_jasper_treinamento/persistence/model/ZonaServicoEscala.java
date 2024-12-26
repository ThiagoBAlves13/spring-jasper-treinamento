package com.treinamentoJasper.spring_jasper_treinamento.persistence.model;

import java.io.Serializable;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 
 * Classe Modelo da Entidade "ZonaPraticagem".
 * 
 * @author Núcleo de Implantação da Fábrica de Software - CASNAV, 2021
 *
 */

@Data
@Entity
@Immutable
@Table(name = "ZONAESCALA")
public class ZonaServicoEscala implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ZS")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "CD_ZS")
    private String codigo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "DS_ZS")
    private String descricao;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "CD_UF")
    private String uf;

    @Column(name = "FG_ATIVO")
    private Boolean fgAtivo;
       
}
