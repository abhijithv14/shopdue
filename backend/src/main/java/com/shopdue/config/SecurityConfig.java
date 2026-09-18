package com.shopdue.config;
import org.springframework.context.annotation.*; import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.web.SecurityFilterChain; import org.springframework.web.cors.CorsConfiguration; import org.springframework.web.cors.CorsConfigurationSource; import org.springframework.web.cors.UrlBasedCorsConfigurationSource; import java.util.List;
@Configuration public class SecurityConfig {
 @Bean SecurityFilterChain filter(HttpSecurity http)throws Exception{http.csrf(c->c.disable()).cors(c->{}).authorizeHttpRequests(a->a.anyRequest().permitAll()); return http.build();}
 @Bean CorsConfigurationSource cors(){var c=new CorsConfiguration(); c.setAllowedOrigins(List.of("*")); c.setAllowedMethods(List.of("*")); c.setAllowedHeaders(List.of("*")); var s=new UrlBasedCorsConfigurationSource(); s.registerCorsConfiguration("/**",c); return s;}
}