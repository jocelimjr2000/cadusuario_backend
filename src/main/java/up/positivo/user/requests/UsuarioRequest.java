package up.positivo.user.requests;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class UsuarioRequest {

	@NotNull(message = "Preenchimento Obrigatório")
	@Length(min = 11, max = 11, message = "Número inválido")
	private String cpf;

	@NotNull(message = "Preenchimento Obrigatório")
	private String nome;

	@NotNull(message = "Preenchimento Obrigatório")
	private String email;

	@NotNull(message = "Preenchimento Obrigatório")
	private Date dtNascimento;

	@Min(value = 1, message = "Nível incorreto")
	@Max(value = 3, message = "Nível incorreto")
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
}
