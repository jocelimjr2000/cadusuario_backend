package up.positivo.user.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Usuario {

	@Id
	@Column(nullable = false, length = 11)
	@Length(min = 11, max = 11, message = "Número inválido")
	private String cpf;

	@Column(nullable = false, length = 200)
	private String nome;

	@Column(nullable = false, length = 100)
	private String email;
	
	@JsonIgnore
	@Column(nullable = false, length = 300)
	private String senha;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dtNascimento;
	
	// P - Pendente
	// A - Aprovado
	// R - Reprovado
	@JsonIgnore
	@Column(nullable = false, length = 1)
	private String aprovado = "P";

	// 1 = hóspede
	// 2 = recepção
	// 3 = gerente
	@Column(nullable = false)
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

	public String isAprovado() {
		return aprovado;
	}

	public void setAprovado(String aprovado) {
		this.aprovado = aprovado;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

}
