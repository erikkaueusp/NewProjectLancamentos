package br.com.meuprojetoalga.projetoalga.service;

import br.com.meuprojetoalga.projetoalga.model.Pessoa;
import br.com.meuprojetoalga.projetoalga.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;


    public Pessoa atualiza(Long codigo, Pessoa pessoa) {

        Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);

        //classe utilitária pra ajudar a copiar os dados da pessoa e atualzar para a nova classe beanutils da org.springframework
        //consiste em copiar os dados pessoa para pessoa salva, ignorando o codigo.
        BeanUtils.copyProperties(pessoa,pessoaSalva,"codigo");
        return pessoaRepository.save(pessoaSalva);
    }

    private Pessoa buscarPessoaPeloCodigo(Long codigo) {

        Pessoa pessoaSalva = pessoaRepository.getById(codigo);
        if (pessoaSalva == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return pessoaSalva;
    }

    public void atualizaPropriedadeAtivo(Long codigo,Boolean ativo) {
        Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);

    }




}
