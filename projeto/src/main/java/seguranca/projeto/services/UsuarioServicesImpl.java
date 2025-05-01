package seguranca.projeto.services;

import java.util.Base64;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import seguranca.projeto.Repositories.UsuarioRepository;
import seguranca.projeto.dtos.users.UsuarioRequestDto;
import seguranca.projeto.dtos.users.UsuarioResponseDto;
import seguranca.projeto.entities.Usuario;
import seguranca.projeto.mappers.UsuarioMapper;
import seguranca.projeto.dtos.logins.*;

@Service
@RequiredArgsConstructor
public class UsuarioServicesImpl implements UsuarioServices{

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public List<UsuarioResponseDto> findAll() {
        return UsuarioMapper.toDtoList(repository.findAll());
    }

    @Override
    public UsuarioResponseDto findById(Long id) {
        if(repository.existsById(id)){
            return UsuarioMapper.toDto(repository.findById(id).get());
        }
        return null;
    }

    @Override
    public UsuarioResponseDto create(UsuarioRequestDto request) {
        Usuario usuario = new Usuario();
        usuario = UsuarioMapper.toEntity(request);

        usuario.setPassword(encoder.encode(request.getPassword()));
        
        usuario = repository.save(usuario);
        return UsuarioMapper.toDto(usuario);
    }

    @Override
    public UsuarioResponseDto update(Long id, UsuarioRequestDto request) {
        if(!repository.existsById(id)){
            throw new IllegalArgumentException("não foi possivel atualizar por id");
        }

        Usuario usuario = repository.findById(id).get();
        
        usuario.setName(request.getName());
        usuario.setPassword(encoder.encode(request.getPassword()));
        usuario.setRole(request.getRole());
        usuario.setUsername(request.getUsername());

        usuario = repository.save(usuario);
        return UsuarioMapper.toDto(usuario);
    }

    @Override
    public void delete(Long id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    @Override
    public LoginResponseDto autenticate(LoginRequestDto dto){
        Usuario usuario = repository.findByUsername(dto.getUsername()).orElseThrow();
        if(!encoder.matches(dto.getPassword(), usuario.getPassword())){
            throw new BadCredentialsException("invalid username or password");
        }


        /*
                         Basic emV6aW5obzpsb2xv' \

                              "emV6aW5obzpsb2xv"
         */
        String token = Base64.getEncoder().encodeToString(
            (usuario.getUsername() + ":" + dto.getPassword()).getBytes()
        );
        

        return LoginResponseDto.builder().type("Basic").token(token).build();
    }


}
