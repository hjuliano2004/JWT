package seguranca.projeto.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import seguranca.projeto.enums.UserRole;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 16, nullable = false)
    private String name;

    @Column(length = 32, nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserRole role = UserRole.USER;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> role.name());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Indica que a conta não está expirada
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Indica que a conta não está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Indica que as credenciais não estão expiradas
    }

    @Override
    public boolean isEnabled() {
        return true; // Indica que o usuário está ativo
    }


    /*
     * PasccalCase - nome de classes
     * camelCase - nome de atributos e metodos
     * snake_case - nome de banco de dados
     * kebab-case - tambem no aplicationProperties
     */
}