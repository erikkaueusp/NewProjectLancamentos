package br.com.meuprojetoalga.projetoalga.model;

import br.com.meuprojetoalga.projetoalga.model.enums.TipoLancamento;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Lancamento")
@Data
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotNull
    private String descricao;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @NotNull
    private BigDecimal valor;

    private String observacao;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoLancamento tipoLancamento;

    @NotNull
    @ManyToOne(fetch =FetchType.LAZY)
    private Categoria categoria;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Pessoa pessoa;



}
