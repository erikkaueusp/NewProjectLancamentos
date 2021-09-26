package br.com.meuprojetoalga.projetoalga.service;

import br.com.meuprojetoalga.projetoalga.model.Lancamento;
import br.com.meuprojetoalga.projetoalga.repository.CategoriaRepository;
import br.com.meuprojetoalga.projetoalga.repository.LancamentoRepository;
import br.com.meuprojetoalga.projetoalga.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;


    public void validacaoPostman(Lancamento lancamento) {

        // Cria uma categoria para o lançamento caso não exista, validação via API
        if(!categoriaRepository.existsById(lancamento.getCategoria().getCodigo())) {
            categoriaRepository.save(lancamento.getCategoria());
        }
        if (!pessoaRepository.existsById(lancamento.getPessoa().getCodigo())) {
            pessoaRepository.save(lancamento.getPessoa());
        }

    }



}
