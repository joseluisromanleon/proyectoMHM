package org.iesvdm.mhm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //.cors -> cors.disable()                //  Desabilita Cors
                //.cors(Customizer.withDefaults())    //  Habilitar Cors
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                       .requestMatchers("/v1/api/usuarios").permitAll() // ✅ Todas las rutas bajo /v1/api son públicas
                       .requestMatchers("/usuarios").permitAll()  // ruta pública específica no necesita estar logueado
                                // 🔐 Las demás rutas necesitan autenticación
                        .requestMatchers("/v1/api/auth/login").permitAll()
                        .anyRequest().permitAll()
                );
                        //      configuracion para producción
//                .requestMatchers("/v1/api/public/**").permitAll() // Endpoints públicos
//                .requestMatchers("/v1/api/admin/**").hasRole("ADMIN") // Solo admin
//                .requestMatchers("/v1/api/user/**").hasAnyRole("USER", "ADMIN") // User o admin
//                .anyRequest().authenticated()
//            )
//            .httpBasic(Customizer.withDefaults()); // O usa JWT si lo prefieres

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
