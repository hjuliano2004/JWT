package seguranca.projeto.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import seguranca.projeto.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByUsername(String username);
    
}
