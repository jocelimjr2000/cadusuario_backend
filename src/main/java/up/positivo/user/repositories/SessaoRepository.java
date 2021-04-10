package up.positivo.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import up.positivo.user.entities.Sessao;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {
	
	boolean existsByToken(String token);
}
