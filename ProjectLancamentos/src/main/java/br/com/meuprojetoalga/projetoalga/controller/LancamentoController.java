package br.com.meuprojetoalga.projetoalga.controller;

import br.com.meuprojetoalga.projetoalga.event.RecursoCriadoEvent;
import br.com.meuprojetoalga.projetoalga.entidades.Lancamento;
import br.com.meuprojetoalga.projetoalga.repository.CategoriaRepository;
import br.com.meuprojetoalga.projetoalga.repository.LancamentoRepository;
import br.com.meuprojetoalga.projetoalga.repository.filters.LancamentoFilter;
import br.com.meuprojetoalga.projetoalga.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    //filtrar com paginacao, coloca o parametro pageable para que na url se passe size e page.

    @GetMapping
    public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return lancamentoRepository.filtrar(lancamentoFilter, pageable);
    }

    @PostMapping
    public  ResponseEntity<Lancamento> cria(@Valid  @RequestBody Lancamento lancamento, HttpServletResponse response) {

            lancamentoService.validacaoPostman(lancamento);
            Lancamento novoLancamento =lancamentoRepository.save(lancamento);

            applicationEventPublisher.publishEvent(new RecursoCriadoEvent(
                    this,
                    response,
                    novoLancamento.getCodigo())
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(novoLancamento);

    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamento> buscaUm(@PathVariable Long codigo) {

        Lancamento lancamento = lancamentoRepository.getById(codigo);

        return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo) {
        lancamentoRepository.deleteById(codigo);
    }


}
