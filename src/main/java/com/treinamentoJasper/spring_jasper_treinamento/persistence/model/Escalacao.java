package com.treinamentoJasper.spring_jasper_treinamento.persistence.model;

import java.io.Serializable;

import org.springframework.data.rest.core.annotation.RestResource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Basic;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ESCALACAO")
public class Escalacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ESCALACAO")
    private Integer id;

    @NotNull
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_ESCALA")
    @RestResource(exported = false)
    private Escala escala;

    @NotNull
    @JsonProperty(access = Access.WRITE_ONLY)
    @ManyToOne(optional = false)
    @RestResource(exported = false)
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @NotNull
    @Column(name = "NR_DIA")
    @Min(1) @Max(31)
    private short nrDia;

    @NotNull
    @NotBlank
    @Pattern(regexp = "(Normal)|(Standby)")
    @Column(name = "DS_TIPO_ESCALACAO")
    private String dsTipoEscalacao;
    
    @NotNull
    @Column(name = "FG_ATIVO")
    private boolean fgAtivo;

}