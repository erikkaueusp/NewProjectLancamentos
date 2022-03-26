package br.com.meuprojetoalga.projetoalga.repository.querys;

import br.com.meuprojetoalga.projetoalga.entidades.Lancamento;
import br.com.meuprojetoalga.projetoalga.repository.filters.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        //criar as restrições
        Predicate [] predicates = criarRestricoes(lancamentoFilter,builder,root);
        criteria.where(predicates);

        TypedQuery<Lancamento> query = manager.createQuery(criteria);
        adicionarRestricoesPaginacaoQuery(query,pageable);


        //conteudo, o proprio pageable e o total (quantidade de elementos)
        return new PageImpl<>(query.getResultList(),pageable,total(lancamentoFilter));

    }

    private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter
            , CriteriaBuilder builder, Root<Lancamento> root) {

        List<Predicate> predicates = new ArrayList<>();

        // where descricao like '%sadasd%'
        if(!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
            predicates.add(builder.like(
                    builder.lower(root.get("descricao")), "%"+ lancamentoFilter.getDescricao().toLowerCase() +
                    "%"));

        }
        if(lancamentoFilter.getDataVencimentoDe() != null) {
            predicates.add(
                    builder.greaterThanOrEqualTo(root.get("dataVencimentoDe"), lancamentoFilter.getDataVencimentoDe())
            );

        }
        if(lancamentoFilter.getDataVencimentoAte() != null) {
            predicates.add(
                    builder.lessThanOrEqualTo(root.get("dataVencimentoAte"), lancamentoFilter.getDataVencimentoAte())
            );

        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }


    private void adicionarRestricoesPaginacaoQuery(TypedQuery<Lancamento> query, Pageable pageable) {

        //Pega a pagina total
        int paginaAtual = pageable.getPageNumber();
        // Total de registro por pagina
        int totalRegPagina = pageable.getPageSize();
        // Primeiro registro da pagina
        int primeiroRegPagina = paginaAtual*totalRegPagina;

        query.setFirstResult(primeiroRegPagina);
        query.setMaxResults(totalRegPagina);

    }

    private Long total(LancamentoFilter lancamentoFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(lancamentoFilter,builder,root);
        criteria.where(predicates);

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();
    }

}
