package seguranca.projeto.dtos.users;

import lombok.Builder;
import lombok.Data;
import seguranca.projeto.enums.UserRole;

@Data
@Builder
public class UsuarioRequestDto {
    private String name;
    private String username;
    private String password;
    private UserRole  role;
}