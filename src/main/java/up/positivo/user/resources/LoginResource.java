package up.positivo.user.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import up.positivo.user.CustomErrors;
import up.positivo.user.models.Usuario;
import up.positivo.user.repositories.UsuarioRepository;
import up.positivo.user.validations.LoginValidation;

@RestController
@RequestMapping("/login")
public class LoginResource extends CustomErrors {

	@Autowired
	UsuarioRepository usuarioRepository;

	@PostMapping()
	@ApiOperation(value = "Logar")
	public ResponseEntity<Usuario> login(@Valid @RequestBody LoginValidation loginValidation) {
		try {

			String email = loginValidation.getEmail();
			String senha = loginValidation.getSenha();

			Usuario usuario = usuarioRepository.findByEmailAndSenha(email, senha);

			if (usuario != null) {
				return new ResponseEntity<>(usuario, HttpStatus.OK);
			}

			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
