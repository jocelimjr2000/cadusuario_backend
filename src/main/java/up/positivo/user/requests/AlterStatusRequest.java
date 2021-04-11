package up.positivo.user.requests;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;



public class AlterStatusRequest {

	@NotNull(message = "Preenchimento Obrigatório")
	@Length(min = 11, max = 11, message = "Número inválido")
	private String cpf;
	
	@NotNull(message = "Preenchimento Obrigatório")
	private boolean aprovar;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public boolean isAprovar() {
		return aprovar;
	}

	public void setAprovar(boolean aprovar) {
		this.aprovar = aprovar;
	}

	
	
}
