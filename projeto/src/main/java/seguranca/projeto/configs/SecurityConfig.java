package seguranca.projeto.configs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import seguranca.projeto.enums.UserRole;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean 
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login").permitAll()

                        .requestMatchers(HttpMethod.GET, "organizacoes/**").hasAnyAuthority(
                            UserRole.ADMIN.name(),
                            UserRole.USER.name())

                        .requestMatchers(HttpMethod.POST, "organizacoes/**").hasAuthority(
                            UserRole.ADMIN.name())
                        
                        .requestMatchers(HttpMethod.PUT, "organizacoes/**").hasAuthority(
                            UserRole.ADMIN.name())

                        .requestMatchers(HttpMethod.DELETE, "organizacoes/**").hasAuthority(
                            UserRole.ADMIN.name())

                        .requestMatchers("/usuarios/**").hasAuthority(
                            UserRole.ADMIN.name())    //.hasRole("ADMIN")
                       
                            .anyRequest().authenticated());

                        return http.build();
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