package up.positivo.user.requests;


import javax.validation.constraints.NotNull;


public class LoginRequest {

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
