package com.treinamentoJasper.spring_jasper_treinamento.persistence.model;


import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "escala")
public class Escala implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESCALA")
    private Integer id;

    @NotNull
    @JoinColumn(name = "ID_ZS")
    @ManyToOne(optional = false)
    private ZonaServicoEscala zs;

    @NotNull
    @Min(1) @Max(12)
    @Column(name = "NR_MES")
    private short mes;

    @NotNull
    @Column(name = "NR_ANO")
    private int ano;

    @Column(name = "DT_SITUACAO_MODIFICACAO")
    private LocalDateTime dtModSituacao;
    
    @NotNull
    @Column(name = "DS_SITUACAO")
    private String situacao;
}
