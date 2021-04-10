package up.positivo.user.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Sessao {

	@Id
	@Column(length = 11, nullable = false, updatable = false, insertable = false)
	private String cpf;

	@JsonIgnore
	@MapsId("cpf")
	@OneToOne
	@JoinColumn(name = "cpf")
	private Usuario usuario;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String token;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
