package seguranca.projeto.Controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import seguranca.projeto.dtos.logins.LoginRequestDto;
import seguranca.projeto.dtos.logins.LoginResponseDto;
import seguranca.projeto.services.UsuarioServices;

@RequiredArgsConstructor
@RestController
@RequestMapping("login")
public class LoginController {

    private final UsuarioServices userService;

    @PostMapping
    public LoginResponseDto login(@RequestBody LoginRequestDto dto){

        return userService.autenticate(dto);
    }
    
}
