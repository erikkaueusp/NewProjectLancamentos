package br.com.meuprojetoalga.projetoalga.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "pessoas")
public class Pessoa {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotNull
    private String nome;

    @Embedded
    private Endereco endereco;

    @NotNull
    private boolean ativo;


}
