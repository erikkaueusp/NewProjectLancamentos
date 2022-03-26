package br.com.meuprojetoalga.projetoalga.controller;

import br.com.meuprojetoalga.projetoalga.controller.form.LoginForm;
import br.com.meuprojetoalga.projetoalga.model.TokenModel;
import br.com.meuprojetoalga.projetoalga.service.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {


    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenModel> autenticar(@RequestBody @Valid LoginForm form) {
        UsernamePasswordAuthenticationToken authenticationToken = form.converter();

        try {
            Authentication authentication = authManager.authenticate(authenticationToken);
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(new TokenModel(token,"Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }

    }
}
