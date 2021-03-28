package up.positivo.user.validations;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
public class LoginValidation {

	@NotNull(message = "Preenchimento Obrigatório")
	private String email;
	
	@NotNull(message = "Preenchimento Obrigatório")
	private String senha;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
