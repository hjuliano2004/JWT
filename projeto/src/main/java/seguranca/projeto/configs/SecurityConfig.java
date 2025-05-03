package seguranca.projeto.configs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import seguranca.projeto.enums.UserRole;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean 
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement((s) -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login").permitAll()

                        .requestMatchers(HttpMethod.GET, "organizacoes/**").hasAnyAuthority(
                            UserRole.ADMIN.name(),
                            UserRole.USER.name())

                        .requestMatchers("organizacoes/**").hasAuthority(
                            UserRole.ADMIN.name())    //.hasRole("ADMIN")
                       
                            .anyRequest().authenticated())

                            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

                            return http.build();
    }


    @Bean
    public AuthenticationManager autenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }












//cria um usuario pré definido
@Bean
@ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        String password = encoder.encode("password");
        UserDetails userDetails = User.withUsername("user")
            .password(password)
           .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(userDetails);
    }
}

//https://spring.io/guides/


 //.requestMatchers("/projeto/**").hasAnyAuthority(UserRole.ADMIN.name(),
 // UserRole.USER.name())//verifica mais de uma autoridade