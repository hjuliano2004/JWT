package seguranca.projeto.dtos.organizacao;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizacaoRequestDto {
    @NotBlank(message = "o nome é obrigatório")
    private String name;

    @NotBlank(message = "o nome é obrigatório")
    private String email;
}
