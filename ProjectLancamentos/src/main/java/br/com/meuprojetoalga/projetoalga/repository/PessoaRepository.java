package br.com.meuprojetoalga.projetoalga.repository;

import br.com.meuprojetoalga.projetoalga.entidades.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa,Long> {

}
