package seguranca.projeto.dtos.users;

import lombok.Builder;
import lombok.Data;
import seguranca.projeto.enums.UserRole;

@Data
@Builder
public class UsuarioResponseDto {
    private Long id;
    private String name;
    private String username;
    private UserRole  role;
}
