package br.com.meuprojetoalga.projetoalga.controller.form;

import br.com.meuprojetoalga.projetoalga.entidades.Usuario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
public class LoginForm {

    private String usuario;
    private String senha;

    public UsernamePasswordAuthenticationToken converter() {
        return new UsernamePasswordAuthenticationToken(usuario,senha);
    }
}
