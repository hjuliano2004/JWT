package seguranca.projeto.entities.initializer;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import seguranca.projeto.Repositories.UsuarioRepository;
import seguranca.projeto.entities.Usuario;
import seguranca.projeto.enums.UserRole;

@Component
@RequiredArgsConstructor
public class UsuarioInitializer {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    @PostConstruct
    public void init() {
        // Verifica se o usuário já existe
        if (repository.findByUsername("zezinho").isEmpty()) {
            // Cria um novo usuário
            Usuario usuario = new Usuario();
            usuario.setName("José");
            usuario.setUsername("zezinho");
            usuario.setPassword(encoder.encode("lolo")); // Criptografa a senha
            usuario.setRole(UserRole.ADMIN); // Define o papel como ADMIN

            // Salva no banco de dados
            repository.save(usuario);
            System.out.println("\n\n\n\n\n\nUSUARIO ADMINISTRADOR  CRIADO!\n\n\n\n\n");
        } else {
            System.out.println("\n\n\n\n\n\nUSUARIO ADMINISTRADOR JA EXISTE.\n\n\n\n\n\n\n");
        }
    }
}