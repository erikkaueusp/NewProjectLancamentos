package br.com.meuprojetoalga.projetoalga.config.security;

import br.com.meuprojetoalga.projetoalga.repository.UsuarioRepository;
import br.com.meuprojetoalga.projetoalga.service.security.AutencicacaoService;
import br.com.meuprojetoalga.projetoalga.service.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // O enable já vem com o a anotação Configuration que permite o modulo do spring reconhecer
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutencicacaoService autenticacaoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override // Configurações de autenticação
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication() // autenticação em memória
//                .withUser("admin").password("{noop}admin").roles("ADMIN"); // Note que utilizamos {noop} antes da senha para indicar que não será criptografada.

        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());    // Aqui se coloca o serviço de autenticação

    }

    @Override // configurações de autorização urls
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.GET,"/categorias").permitAll()
                .antMatchers(HttpMethod.POST,"/auth").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic().and()
                .formLogin().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // Evita criar session.
                .headers().frameOptions().sameOrigin().and()
                .addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, repository), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable(); // caso rolasse um javascript injection
    }

}