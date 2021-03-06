package up.positivo.user.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import up.positivo.user.CustomErrors;
import up.positivo.user.entities.Usuario;
import up.positivo.user.repositories.UsuarioRepository;
import up.positivo.user.requests.AlterStatusRequest;
import up.positivo.user.requests.UsuarioRequest;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioResource extends CustomErrors {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@PostMapping("/")
	@ApiOperation(value = "Cadastrar Usuário")
	public ResponseEntity<Usuario> create(@Valid @RequestBody UsuarioRequest usuarioRequest, @RequestAttribute("usuarioNivel") int nvLogado) {
		try {
			
			int nvCad = usuarioRequest.getNivel();
			String cpf = usuarioRequest.getCpf();

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
			usuario.setNome(usuarioRequest.getNome());
			usuario.setEmail(usuarioRequest.getEmail());
			usuario.setSenha(usuarioRequest.getCpf());
			usuario.setDtNascimento(usuarioRequest.getDtNascimento());
			usuario.setNivel(nvCad);

			usuario = usuarioRepository.save(usuario);

			return new ResponseEntity<>(usuario, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/atualizar")
	@ApiOperation(value = "Atualizar Usuário")
	public ResponseEntity<Usuario> update(@Valid @RequestBody UsuarioRequest usuarioRequest, @RequestAttribute("usuarioNivel") int nvLogado) {
		try {

			// Puxa o usuario do banco a partir do cpf validation
			String cpf = usuarioRequest.getCpf();
			Usuario usuario = usuarioRepository.findByCpf(cpf);

			if (usuario == null) {
				return this.singleErrorException("error", "CPF não cadastrado");
			}

			// Pega nivel do usuario do banco
			int nvCad = usuario.getNivel();

			if (nvLogado == (int) 1) {
				return this.singleErrorException("error", "usuários do nível 1 não podem alterar outros usuários");
			}

			if (nvLogado == 2 && nvCad > 1) {
				return this.singleErrorException("error", "usuários do nível 2 só podem alterar usuários do nível 1");
			}

			if (nvLogado == 2 && usuarioRequest.getNivel() > 2) {
				return this.singleErrorException("error",
						"usuários do nivel 2 não podem alterar o nível de outros usuários para 3");
			}

			usuario.setNome(usuarioRequest.getNome());
			usuario.setEmail(usuarioRequest.getEmail());
			usuario.setDtNascimento(usuarioRequest.getDtNascimento());
			usuario.setNivel(usuarioRequest.getNivel());

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
				return new ResponseEntity<>(usuarioRepository.findByAprovadoOrderByNomeAsc(status), HttpStatus.OK);
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

			if (usuario != null) {
				return new ResponseEntity<>(usuario, HttpStatus.OK);
			}

			return this.singleErrorException("error", "CPF não cadastrado");

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/alterarstatus")
	@ApiOperation(value = "Alterar Status")
	public ResponseEntity<Usuario> alterarStatus(@Valid @RequestBody AlterStatusRequest alterStatusRequest, @RequestAttribute("usuarioNivel") int nvLogado) {
		try {
			
			String cpf = alterStatusRequest.getCpf();
			
			//Puxa Usuario do Banco
			Usuario usuario = usuarioRepository.findByCpf(cpf);

			if (usuario == null) {
				return this.singleErrorException("error", "CPF não cadastrado");
			}

			String userStatus = usuario.isAprovado();

			int nvCad = usuario.getNivel();

			// Verifica o status do usuario
			if (!userStatus.equalsIgnoreCase("P")) {
				return this.singleErrorException("error", "Esse usuário não está mais pendente");
			}
			

			if (nvLogado < (int) 2) {
				return this.singleErrorException("error", "usuários do nivel 1 não podem realizar operações de Aprovação");
			}

			if (nvLogado == 2 && nvCad > 1) {
				return this.singleErrorException("error", "usuários do nivel 2 podem apenas aprovar/reprovar usuários do nivel 1");
			}

		
			if (alterStatusRequest.isAprovar()) {
				//Se a boolean é true, então é aprovado
				return new ResponseEntity<>(this.alterStatus(cpf, "A"), HttpStatus.OK);
			}
			
			return new ResponseEntity<>(this.alterStatus(cpf, "R"), HttpStatus.OK);
			

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
