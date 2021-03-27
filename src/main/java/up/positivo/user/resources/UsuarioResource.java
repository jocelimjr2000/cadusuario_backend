package up.positivo.user.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioResource extends CustomErrors {

	@Autowired
	UsuarioRepository usuarioRepository;

	@GetMapping("/")
	@ApiOperation(value = "Listar todos os usuários")
	public ResponseEntity<List<Usuario>> list() {
		try {

			List<Usuario> usuario = usuarioRepository.findAll();

			return new ResponseEntity<>(usuario, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/pendente")
	@ApiOperation(value = "Listar todos os usuários pendentes de ativação")
	public ResponseEntity<List<Usuario>> list2() {
		try {

			List<Usuario> usuario = usuarioRepository.findByAprovadoFalse();

			return new ResponseEntity<>(usuario, HttpStatus.CREATED);

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
	
	@PostMapping("/")
	@ApiOperation(value = "Cadastrar Usuário")
	public ResponseEntity<Usuario> create(@Valid @RequestBody Usuario usuario) {
		try {
			
			Usuario verificarUsuario = usuarioRepository.findByCpf(usuario.getCpf());
			
			if(verificarUsuario == null) {
				int nivel = usuario.getNivel();
				
				if(nivel != 1) {
					
					// Regras de aprovação
					
					
				}
				
				return new ResponseEntity<>(usuarioRepository.save(usuario), HttpStatus.CREATED);

			}
			
			return new ResponseEntity<>(null, HttpStatus.FOUND);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/{cpf}")
	@ApiOperation(value = "Atualizar Usuário")
	public ResponseEntity<Usuario> update(@Valid @RequestBody Usuario usuario) {
		try {
			
			Usuario verificarUsuario = usuarioRepository.findByCpf(usuario.getCpf());
			
			if(verificarUsuario != null) {
				
				return new ResponseEntity<>(usuarioRepository.save(usuario), HttpStatus.CREATED);

			}
			
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
