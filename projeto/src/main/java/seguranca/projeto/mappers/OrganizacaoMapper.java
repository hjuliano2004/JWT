package seguranca.projeto.mappers;

import java.util.List;

import seguranca.projeto.dtos.organizacao.*;
import seguranca.projeto.entities.Organizacao;

public class OrganizacaoMapper {
    private OrganizacaoMapper(){};

    public static Organizacao toEntity(OrganizacaoRequestDto request){

        Organizacao organizacao = new Organizacao();

        organizacao.setName(request.getName());
        organizacao.setEmail(request.getEmail());

        return organizacao;
    }

    public static OrganizacaoResponseDto toDto(Organizacao entity){
        return OrganizacaoResponseDto.builder()
        .id(entity.getId())
        .name(entity.getName())
        .email(entity.getEmail()).build();
    }

    public static List<OrganizacaoResponseDto> toDtoList(List<Organizacao> organizacoes){
        return organizacoes.stream()
        .map(OrganizacaoMapper::toDto)
        .toList();
    }
    
}
