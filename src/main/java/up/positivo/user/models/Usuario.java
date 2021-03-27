package up.positivo.user.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
public class Usuario {

	@Id
	@Column(nullable = false, length = 11)
	@NotNull(message = "Preenchimento Obrigatório")
	@Length(min = 11, max = 11, message = "Número inválido")
	private String cpf;

	@Column(nullable = false, length = 200)
	@NotNull(message = "Preenchimento Obrigatório")
	private String nome;

	@Column(nullable = false, length = 100)
	@NotNull(message = "Preenchimento Obrigatório")
	private String email;

	@Column(nullable = false, length = 300)
	@NotNull(message = "Preenchimento Obrigatório")
	private String senha;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Preenchimento Obrigatório")
	private Date dtNascimento;

	@Column(nullable = false)
	private boolean aprovado = false;

	// 1 = hóspede
	// 2 = recepção
	// 3 = gerente
	@Column(nullable = false)
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

	public boolean isAprovado() {
		return aprovado;
	}

	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

}
