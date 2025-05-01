package seguranca.projeto.dtos.organizacao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizacaoResponseDto {
    private Long id;
    private String name;
    private String email;
}
