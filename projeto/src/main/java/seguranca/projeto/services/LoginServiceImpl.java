package seguranca.projeto.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import seguranca.projeto.configs.JwtComponent;
import seguranca.projeto.dtos.logins.*;
import seguranca.projeto.entities.Usuario;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final JwtComponent jwtComponent;
    private final AuthenticationManager authenticationManager;

        @Override
    public LoginResponseDto authenticate(LoginRequestDto dto){
        /* 
        Usuario usuario = repository.findByUsername(dto.getUsername()).orElseThrow();
        if(!encoder.matches(dto.getPassword(), usuario.getPassword())){
            throw new BadCredentialsException("invalid username or password");
        }
        String token = Base64.getEncoder().encodeToString(
            (usuario.getUsername() + ":" + dto.getPassword()).getBytes()
        );*/
        //return LoginResponseDto.builder().type("Basic").token(token).build();

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));


        if (authentication == null || !authentication.isAuthenticated()){
            throw new BadCredentialsException("invalid username or password");
        }

        
        Usuario user = (Usuario) authentication.getPrincipal();

        String token = jwtComponent.generateToken(user);

        return LoginResponseDto.builder().type("Bearer").token(token).build();
    }

    @Override
    public Object getInfo(String token){
        return jwtComponent.parseClaims(token.split(" ")[1]).getBody();
    }
    
}