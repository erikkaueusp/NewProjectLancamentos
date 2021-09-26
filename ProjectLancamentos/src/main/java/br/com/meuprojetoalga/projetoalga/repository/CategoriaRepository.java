package br.com.meuprojetoalga.projetoalga.repository;

import br.com.meuprojetoalga.projetoalga.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {

}
