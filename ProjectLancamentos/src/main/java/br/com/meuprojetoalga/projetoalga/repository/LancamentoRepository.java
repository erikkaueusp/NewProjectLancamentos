package br.com.meuprojetoalga.projetoalga.repository;


import br.com.meuprojetoalga.projetoalga.entidades.Lancamento;
import br.com.meuprojetoalga.projetoalga.repository.querys.LancamentoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento,Long>, LancamentoRepositoryQuery {

}
