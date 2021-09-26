package br.com.meuprojetoalga.projetoalga.controller;

import br.com.meuprojetoalga.projetoalga.event.RecursoCriadoEvent;
import br.com.meuprojetoalga.projetoalga.model.Categoria;
import br.com.meuprojetoalga.projetoalga.model.Pessoa;
import br.com.meuprojetoalga.projetoalga.repository.PessoaRepository;
import br.com.meuprojetoalga.projetoalga.service.PessoaService;
import org.apache.catalina.core.ApplicationPushBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaRepository pessoaRepository;

    //Dado que um evento fora criado e o listener, basta chamar o publicador de eventos de aplicação

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    
    public List<Pessoa> listar() {
        return pessoaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {

        pessoaRepository.save(pessoa);

        applicationEventPublisher.publishEvent(new RecursoCriadoEvent(
                this,
                response,
                pessoa.getCodigo())
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
        
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo) {
        pessoaRepository.deleteById(codigo);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo,@Valid @RequestBody Pessoa pessoa) {
        // aqui é uma atualização geral
        Pessoa pessoaSalva = pessoaService.atualiza(codigo,pessoa);
        return ResponseEntity.ok(pessoaSalva);
    }

    // Atualização parcial
    @PutMapping("/{codigo}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarPropriedadeAtivo(@PathVariable Long codigo,@RequestBody Boolean ativo) {
        // aqui é uma atualização parcial
        pessoaService.atualizaPropriedadeAtivo(codigo, ativo);
    }
    
}
