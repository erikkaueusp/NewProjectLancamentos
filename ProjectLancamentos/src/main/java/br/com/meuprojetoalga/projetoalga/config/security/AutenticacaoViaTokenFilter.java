package br.com.meuprojetoalga.projetoalga.config.security;

import br.com.meuprojetoalga.projetoalga.entidades.Usuario;
import br.com.meuprojetoalga.projetoalga.repository.UsuarioRepository;
import br.com.meuprojetoalga.projetoalga.service.security.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

// Classe que herda de um filtro do spring que é chamada uma única vez a cada requisição


public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;

    private UsuarioRepository repository;

    public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = recuperarToken(request);
        boolean valido = tokenService.isTokenValido(token);
        if(valido) {
           autenticarCliente(token);
        }


        filterChain.doFilter(request,response); // filtro que faz seguir o caminho normal da requisição
    }

    private void autenticarCliente(String token) {
        String username = tokenService.getUsername(token);
        Usuario usuario = repository.findByUsername(username).get();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null || token.isEmpty() || !token.startsWith("Bearer")) {
            return null;
        }

        return token.substring(7,token.length());
    }
}
