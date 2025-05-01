package seguranca.projeto.mappers;

import java.util.List;

import seguranca.projeto.dtos.users.UsuarioRequestDto;
import seguranca.projeto.dtos.users.UsuarioResponseDto;
import seguranca.projeto.entities.Usuario;

public class UsuarioMapper {
    private UsuarioMapper(){};

    public static UsuarioResponseDto toDto(Usuario usuario){

        return UsuarioResponseDto.builder()
        .id(usuario.getId())
        .name(usuario.getName())
        .username(usuario.getUsername())
        .role(usuario.getRole())
        .build();
    }

    public static List<UsuarioResponseDto> toDtoList(List<Usuario> list){
        return list.stream()
        .map(UsuarioMapper::toDto)
        .toList();
    }

    public static Usuario toEntity(UsuarioRequestDto request){
        Usuario usuario = new Usuario();

        usuario.setName(request.getName());
        usuario.setPassword(request.getPassword());
        usuario.setUsername(request.getUsername());
        usuario.setRole(request.getRole());

        return usuario;
    }
    
}
