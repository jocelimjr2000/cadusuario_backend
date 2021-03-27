package up.positivo.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import up.positivo.user.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
