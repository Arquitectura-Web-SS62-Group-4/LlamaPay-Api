package com.wisecoin.LlamaPay_Api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    private static final String[] AUTH_WHITELIST = {
            // --swagger ui
            "v2/api-docs/**",
            "v3/api-docs/**",
            "swagger-resources/**",
            "swagger-ui/**",

            // --login
            "/api/users/login/**",

            // --register
            "/api/users/register/**"
    };


    /*
    1. Cuales son las rutas de los pedidos=request que seran evaluadas
        a. AnyRequest -> Todas las rutas
        b. RequestMatchers -> Todas las rutas de la lista
        c. RequestMatchers con HttpMethod ->Todas las rutas de la lista que hayan sido llamada con determinados metodos HTTP

    2. Cual es la regla de autorizacion para las rutas evaluadas?

        a. permitAll()
        b. authenticated()
        c. hasAnyAuthority()
        d. hasAnyRole()
        e. SpEL - Spring Expresion Language
        f. denyAll()
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        http.cors(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(
                (auth)->auth
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/clients/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/clients/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/cards").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/suscriptions/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/typeBits/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/premiuns/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/dailyBits/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/dailyBits/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/dailyBits/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/users/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/users/**").hasAnyAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET,"/api/clients/{id}").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.PUT,"/api/clients/{id}").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.POST,"/api/cards/client/{id}").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.GET,"/api/cards/client/{id}").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.PUT,"/api/cards/client/{id}").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.DELETE,"/api/cards/{id}").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.GET,"/api/suscriptions/client/{id}").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.GET,"/api/categories").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.POST,"/api/dailyBits/**").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.GET,"/api/dailyBits/client/{id}").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.POST,"/api/goals/**").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.PUT,"/api/goals/**").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.GET,"/api/goals/client/**").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.DELETE,"/api/goals/**").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.POST,"/api/reminders/**").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.PUT,"/api/reminders/**").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.GET,"/api/reminders/client/**").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.DELETE,"/api/reminders/**").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.PUT,"/api/settings/**").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.GET,"/api/settings/**").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.DELETE,"/api/moneyFlows/**").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.POST,"/api/moneyFlows/**").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.PUT,"/api/moneyFlows/**").hasAnyAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.GET,"/api/moneyFlows/client/**").hasAnyAuthority("CLIENTE")
                        .anyRequest().authenticated()
        );


        http.sessionManagement( (session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder();   }
}
