//package com.productms.config;
//
//import java.util.Arrays;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import com.productms.filters.LoginCheckFilter;
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//	@Autowired
//	private LoginCheckFilter loginCheckFilter;
//	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
//			.csrf(csrf -> csrf.disable())
//			.authorizeHttpRequests(req -> req
//					.requestMatchers("products", "product/*").permitAll()
//					.anyRequest().authenticated())
////			.httpBasic(Customizer.withDefaults())
//			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//			.addFilterBefore(loginCheckFilter, UsernamePasswordAuthenticationFilter.class);
//		return http.build();
//	}
//	
//	@Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//	
//}
