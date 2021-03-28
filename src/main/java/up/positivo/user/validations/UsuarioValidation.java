package up.positivo.user.validations;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UsuarioValidation {
	
	
	public interface CreateOrUpdate {

	}

	public interface Login {

	}
	
	@NotNull(message = "Preenchimento Obrigatório", groups = { CreateOrUpdate.class })
	private String cpf;

	@NotNull(message = "Preenchimento Obrigatório", groups = { CreateOrUpdate.class })
	private String nome;

	@NotNull(message = "Preenchimento Obrigatório", groups = { CreateOrUpdate.class, Login.class })
	private String email;
	
	@NotNull(message = "Preenchimento Obrigatório", groups = { Login.class })
	private String senha;

	@NotNull(message = "Preenchimento Obrigatório", groups = { CreateOrUpdate.class })
	private Date dtNascimento;
	
	@Min(value = 1, message = "Nível incorreto", groups = { CreateOrUpdate.class })
	@Max(value = 3, message = "Nível incorreto", groups = { CreateOrUpdate.class })
	private int nivel = 1;
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

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

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	
	
	
	/*
	 * GAMBI
	 */
	
	@Min(value = 1, message = "Nível logado incorreto")
	@Max(value = 3, message = "Nível logado incorreto")
	private int nivelLogado;
	
	public int getNivelLogado() {
		return nivelLogado;
	}

	public void setNivelLogado(int nivelLogado) {
		this.nivelLogado = nivelLogado;
	}
}
