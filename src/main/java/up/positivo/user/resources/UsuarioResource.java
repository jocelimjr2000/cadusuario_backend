package up.positivo.user.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import up.positivo.user.CustomErrors;
import up.positivo.user.models.Usuario;
import up.positivo.user.repositories.UsuarioRepository;

@RestController
@RequestMapping(value = "/user")
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

	@PostMapping("/")
	@ApiOperation(value = "Cadastrar Usuário")
	public ResponseEntity<Usuario> create(@Valid @RequestBody Usuario usuario) {
		try {

			usuario = usuarioRepository.save(usuario);

			return new ResponseEntity<>(usuario, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
