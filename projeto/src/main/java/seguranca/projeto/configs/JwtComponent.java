package seguranca.projeto.configs;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import seguranca.projeto.entities.Usuario;

import java.security.Key;
import java.util.Date;

@Component
public class JwtComponent {
    private final String SECRET_KEY = "bXlTZWNyZXRLZXlGb3JXZWJBcHBsaWNhdGlvbg==";
    //gera a chave para assinar o token
    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

public String generateToken(Usuario usuario){

    //Map<String, Object> map = new HashMap<>();   é possivel mapear o objeto e o adicionar em addClaims
   // map.put("id", usuario.getId());              ao invés de adicionar um claim por vez
   // map.put("username", usuario.getUsername());

    return Jwts.builder()
            .setSubject(usuario.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) // -> 10 
            .claim("id", usuario.getId())
            .claim("name", usuario.getName())
            .claim("role", usuario.getRole())
            //.addClaims(map)                       recebe o map àcima
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
        }

public boolean validateToken(String token){
    try{
        parseClaims(token);
        return true;
    }catch(JwtException | IllegalArgumentException e){
        return false;
    }
}


public Jws<Claims> parseClaims(String token){
    return Jwts.parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token);
}

public String extractUsername(String token){
    return parseClaims(token)
            .getBody()
            .getSubject();
}
    
}
