package br.com.meuprojetoalga.projetoalga;

import br.com.meuprojetoalga.projetoalga.entidades.Endereco;
import br.com.meuprojetoalga.projetoalga.entidades.Pessoa;
import br.com.meuprojetoalga.projetoalga.entidades.Usuario;
import br.com.meuprojetoalga.projetoalga.repository.PessoaRepository;
import br.com.meuprojetoalga.projetoalga.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

@SpringBootApplication
public class ProjetoalgaApplication implements CommandLineRunner {

	private final UsuarioRepository usuarioRepository;

	private final PessoaRepository pessoaRepository;

	public ProjetoalgaApplication(UsuarioRepository usuarioRepository, PessoaRepository pessoaRepository) {
		this.usuarioRepository = usuarioRepository;
		this.pessoaRepository = pessoaRepository;
	}


	public static void main(String[] args) {
		SpringApplication.run(ProjetoalgaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// cadastro de usuario para se logar
		Usuario usuario = new Usuario();
		usuario.setUsername("erik");
		usuario.setSenha(new BCryptPasswordEncoder().encode("123456"));
		usuarioRepository.save(usuario);

		// cadastro no banco de dados

	 for (int i=1; i <= 10; i++) {
	 	Pessoa pessoa = new Pessoa();
	 	pessoa.setNome("Nome " + i);
	 	if (i % 2 == 0) {
	 		pessoa.setAtivo(true);
		}
	 	Endereco endereco = new Endereco();
	 	endereco.setBairro("Bairro " + i);
	 	endereco.setCidade("Cidade " + i);
	 	endereco.setNumero("Numero " + i);
	 	pessoa.setEndereco(endereco);
	 	pessoaRepository.save(pessoa);
	 }
	}
}
