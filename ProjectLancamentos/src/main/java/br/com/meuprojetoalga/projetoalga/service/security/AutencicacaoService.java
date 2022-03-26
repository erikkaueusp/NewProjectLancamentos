package br.com.meuprojetoalga.projetoalga.service.security;

import br.com.meuprojetoalga.projetoalga.entidades.Usuario;
import br.com.meuprojetoalga.projetoalga.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutencicacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = repository.findByUsername(username);
        if(usuario.isPresent()) {
            return usuario.get();
        }
        throw new UsernameNotFoundException("Usuario não encontrado");
    }
}
