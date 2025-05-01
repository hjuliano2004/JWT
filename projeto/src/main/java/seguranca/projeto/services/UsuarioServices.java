package seguranca.projeto.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import seguranca.projeto.dtos.logins.*;
import seguranca.projeto.dtos.users.UsuarioRequestDto;
import seguranca.projeto.dtos.users.UsuarioResponseDto;

public interface UsuarioServices extends UserDetailsService{

    List<UsuarioResponseDto> findAll();

    UsuarioResponseDto findById(Long id);

    UsuarioResponseDto create(UsuarioRequestDto request);

    UsuarioResponseDto update(Long id, UsuarioRequestDto request);

    void delete(Long id);

    LoginResponseDto autenticate(LoginRequestDto dto);
}