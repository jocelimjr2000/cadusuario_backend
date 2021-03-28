package up.positivo.user.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import up.positivo.user.CustomErrors;
import up.positivo.user.models.Usuario;
import up.positivo.user.repositories.UsuarioRepository;
import up.positivo.user.validations.UsuarioValidation;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioResource extends CustomErrors {

	@Autowired
	UsuarioRepository usuarioRepository;

	@PostMapping("/")
	@ApiOperation(value = "Cadastrar Usuário")
	public ResponseEntity<Usuario> create(@Validated(UsuarioValidation.CreateOrUpdate.class) @RequestBody UsuarioValidation usuarioValidation) {
		try {

			int nvLogado = usuarioValidation.getNivelLogado();
			int nvCad = usuarioValidation.getNivel();
			String cpf = usuarioValidation.getCpf();

			if (nvLogado == (int) 1) {
				return this.singleErrorException("error", "usuários do nível 1 não podem cadastrar outros usuários");
			}

			if (nvLogado == 2 && nvCad > 1) {
				return this.singleErrorException("error", "usuários do nível 2 só podem cadastrar usuários do nível 1");
			}

			Usuario verificarUsuario = usuarioRepository.findByCpf(cpf);

			if (verificarUsuario != null) {
				return this.singleErrorException("error", "CPF já cadastrado");
			}

			Usuario usuario = new Usuario();

			usuario.setCpf(cpf);
			usuario.setNome(usuarioValidation.getNome());
			usuario.setEmail(usuarioValidation.getEmail());
			usuario.setSenha(usuarioValidation.getCpf());
			usuario.setDtNascimento(usuarioValidation.getDtNascimento());
			usuario.setNivel(nvCad);

			usuario = usuarioRepository.save(usuario);

			return new ResponseEntity<>(usuario, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/atualizar")
	@ApiOperation(value = "Atualizar Usuário")
	public ResponseEntity<Usuario> update(@Validated(UsuarioValidation.CreateOrUpdate.class) @RequestBody UsuarioValidation usuarioValidation) {
		try {

			int nvLogado = usuarioValidation.getNivelLogado();
			int nvCad = usuarioValidation.getNivel();
			String cpf = usuarioValidation.getCpf();

			if (nvLogado == (int) 1) {
				return this.singleErrorException("error", "usuários do nível 1 não podem cadastrar outros usuários");
			}

			if (nvLogado == 2 && nvCad > 1) {
				return this.singleErrorException("error", "usuários do nível 2 só podem cadastrar usuários do nível 1");
			}

			Usuario usuario = usuarioRepository.findByCpf(cpf);

			if (usuario == null) {
				return this.singleErrorException("error", "CPF não cadastrado");
			}

			usuario.setNome(usuarioValidation.getNome());
			usuario.setEmail(usuarioValidation.getEmail());
			usuario.setDtNascimento(usuarioValidation.getDtNascimento());
			usuario.setNivel(nvCad);

			usuario = usuarioRepository.save(usuario);

			return new ResponseEntity<>(usuario, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/listar/{status}")
	@ApiOperation(value = "Listar todos os usuários por status")
	public ResponseEntity<List<Usuario>> listStatus(@PathVariable("status") String status) {
		try {

			status = status.toUpperCase();

			if (status.equals("A") || status.equals("P") || status.equals("R")) {
				return new ResponseEntity<>(usuarioRepository.findByAprovadoOrderByNomeAsc(status), HttpStatus.CREATED);
			}

			return this.singleErrorException("error", "status inválido");

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{cpf}")
	@ApiOperation(value = "Pesquisar usuário por CPF")
	public ResponseEntity<Usuario> findOne(@PathVariable("cpf") String cpf) {
		try {

			Usuario usuario = usuarioRepository.findByCpf(cpf);

			return new ResponseEntity<>(usuario, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/aprovar/{cpf}")
	@ApiOperation(value = "Reprovar usuário")
	public ResponseEntity<Usuario> aprovar(@PathVariable("cpf") String cpf) {
		try {

			return new ResponseEntity<>(this.alterStatus(cpf, "A"), HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/reprovar/{cpf}")
	@ApiOperation(value = "Reprovar usuário")
	public ResponseEntity<Usuario> reprovar(@PathVariable("cpf") String cpf) {
		try {

			return new ResponseEntity<>(this.alterStatus(cpf, "R"), HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private Usuario alterStatus(String cpf, String status) {
		Usuario usuario = usuarioRepository.findByCpf(cpf);

		usuario.setAprovado(status);

		usuarioRepository.save(usuario);

		return usuario;
	}
}
