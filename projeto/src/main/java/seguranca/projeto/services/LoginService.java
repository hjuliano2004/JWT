package seguranca.projeto.services;

import seguranca.projeto.dtos.logins.*;

public interface LoginService {

    LoginResponseDto authenticate(LoginRequestDto dto);

    Object getInfo(String token);
    
}
