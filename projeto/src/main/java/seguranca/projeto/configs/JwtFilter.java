package seguranca.projeto.configs;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter{

    private final JwtComponent jwtComponent;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
       @NonNull HttpServletRequest request,
       @NonNull HttpServletResponse response,
       @NonNull FilterChain filterChain
        ) throws ServletException, IOException {
            
 String authHeader = request.getHeader("Authorization");

   String jwt = null;
   String username = null;
   if (authHeader != null && authHeader.startsWith("Bearer ")) {
       jwt = authHeader.substring(7);
       username = jwtComponent.extractUsername(jwt);
   }

   SecurityContext context = SecurityContextHolder.getContext();
   if (username != null && context.getAuthentication() == null) {
       UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

   if(jwtComponent.validateToken(jwt)) {
           UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                   userDetails, null, userDetails.getAuthorities());

           context.setAuthentication(authToken);
       }
   }
   //faz o spring dar sequencia na cadeia em grupos
   filterChain.doFilter(request, response);

        }
    
}
