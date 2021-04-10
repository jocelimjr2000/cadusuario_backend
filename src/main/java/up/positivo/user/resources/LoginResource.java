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
import up.positivo.user.entities.Sessao;
import up.positivo.user.entities.Usuario;
import up.positivo.user.models.UserToken;
import up.positivo.user.repositories.SessaoRepository;
import up.positivo.user.repositories.UsuarioRepository;
import up.positivo.user.requests.LoginRequest;
import up.positivo.user.security.JwtTokenUtil;

@RestController
@RequestMapping("/login")
public class LoginResource extends CustomErrors {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	SessaoRepository sessaoRepository;
	
	@Autowired
	JwtTokenUtil jtwTokenUtil;
	
	@PostMapping()
	@ApiOperation(value = "Logar")
	public ResponseEntity<UserToken> login(@Valid @RequestBody LoginRequest loginValidation) {
		try {

			String email = loginValidation.getEmail();
			String senha = loginValidation.getSenha();
			
			// Find user
			Usuario usuario = usuarioRepository.findByEmailAndSenha(email, senha);
			
			// Check user exists
			if (usuario != null) {
				
				// Create token
				String generatedToken = jtwTokenUtil.generateToken(usuario);
				
				// Save token in database
				Sessao sessao = new Sessao();
				sessao.setCpf(usuario.getCpf());
				sessao.setToken(generatedToken);

				sessaoRepository.save(sessao);
				
				// Create response
				UserToken userToke  = new UserToken();
				
				userToke.setCpf(usuario.getCpf());
				userToke.setToken(generatedToken);
				
				return new ResponseEntity<>(userToke, HttpStatus.OK);
			}

			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
