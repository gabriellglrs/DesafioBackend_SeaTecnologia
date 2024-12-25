package br.com.sea.tecnologia.desafioBackend.config.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ResourceServerConfig {
	@Value("${cors.origins}")
	private String corsOrigins;

	@Bean
	@Order(3)
	public SecurityFilterChain rsSecurityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()); // Desativa CSRF (opcional, dependendo do contexto)
		http.cors(cors -> cors.configurationSource(corsConfigurationSource())); // Configura CORS

		http.authorizeHttpRequests(authorize -> authorize
			// Permite acesso ao Swagger sem autenticação
			.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
			// Exige autenticação para qualquer outra rota
			.anyRequest().authenticated()
		);

		http.oauth2ResourceServer(oauth2ResourceServer ->
			oauth2ResourceServer.jwt(Customizer.withDefaults())); // Configura OAuth2 com JWT

		return http.build();
	}

	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
		grantedAuthoritiesConverter.setAuthorityPrefix("");

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		String[] origins = corsOrigins.split(",");

		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOriginPatterns(Arrays.asList(origins));
		corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH"));
		corsConfig.setAllowCredentials(true);
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}

	@Bean
	public CorsFilter corsFilter() {
		return new CorsFilter(corsConfigurationSource());
	}
}
