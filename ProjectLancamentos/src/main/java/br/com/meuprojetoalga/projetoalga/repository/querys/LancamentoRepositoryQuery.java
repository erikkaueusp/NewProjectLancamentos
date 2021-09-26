package br.com.meuprojetoalga.projetoalga.repository.querys;

import br.com.meuprojetoalga.projetoalga.model.Lancamento;
import br.com.meuprojetoalga.projetoalga.repository.filters.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryQuery {

    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

}
