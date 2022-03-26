package br.com.meuprojetoalga.projetoalga.repository;

import br.com.meuprojetoalga.projetoalga.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Optional<Usuario> findByUsername(String username);

}
