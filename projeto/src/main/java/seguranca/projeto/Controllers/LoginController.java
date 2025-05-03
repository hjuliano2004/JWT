package seguranca.projeto.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import seguranca.projeto.dtos.logins.LoginRequestDto;
import seguranca.projeto.dtos.logins.LoginResponseDto;
import seguranca.projeto.services.LoginService;

@RequiredArgsConstructor
@RestController
@RequestMapping("login")
public class LoginController {

    //private final UsuarioServices userService;    autenticate(dto) pode ser no userService
    private final LoginService service;

    @PostMapping
    public LoginResponseDto login(@RequestBody LoginRequestDto dto){

        return service.authenticate(dto);
    }

    @GetMapping
    public Object login(@RequestHeader("Authorization") String token){
        return service.getInfo(token);
    }


    
}
