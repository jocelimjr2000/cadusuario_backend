package up.positivo.user.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import up.positivo.user.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findByCpf(String cpf);
	
	Usuario findByEmailAndSenha(String email, String senha);
	
	List<Usuario> findByAprovadoOrderByNomeAsc(String aprovado);
	
}
