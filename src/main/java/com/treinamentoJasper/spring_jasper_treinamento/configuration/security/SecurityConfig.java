package com.treinamentoJasper.spring_jasper_treinamento.configuration.security;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;

/**
 * Configurações gerais de segurança, utilizando oauth2ResourceServer
 *
 * @see <a href="https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html">
 * OAuth 2.0 Resource Server</a>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {
	
//	@Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
//	String jwkSetUri;
	
    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(request -> request
					.requestMatchers("/").permitAll()
					.requestMatchers("/v2/**").permitAll()
					.requestMatchers("/v3/**").permitAll()
					.requestMatchers("/h2-console/**").permitAll()   //acessar a interface gráfica do banco h2 -> URL:PORT/h2-console
					.requestMatchers("/swagger-ui**").permitAll()
					.requestMatchers("/swagger-ui/**").permitAll()
					.requestMatchers("/swagger-resources/**").permitAll()
					.requestMatchers("/error/**").permitAll()
					.requestMatchers("/api/v1/recursos/**").authenticated()
					.requestMatchers("/api/**").permitAll()
					.anyRequest().denyAll());

		 http.headers().frameOptions().disable(); //acessar a interface gráfica do banco h2 -> URL:PORT/h2-console
		
//		http
//			.oauth2ResourceServer(
//				oauth2 -> oauth2.jwt(Customizer.withDefaults()));  //funcao com ssl ativo (default)
		
//		http.oauth2ResourceServer(oauth2 -> oauth2.jwt(j -> j.decoder(myCustomDecoder()))); //desativa ssl (inseguro)

		return http.build();
	}
	
//	/**
//	 * Método que desabilita checagem de certificado SSL. 
//	 * OBS: Deve-se atentar que em sistemas desenvolvidos para a internet, não se deve usar essa configuração.
//	 * @see <a href="https://howtodoinjava.com/java/java-security/bypass-ssl-certificate-checking-java/">Bypass SSL Certificate Checking in Java</a>
//	 * @return {@link JwtDecoder}
//	 */
//	@Bean
//	public JwtDecoder myCustomDecoder() {
//
//		CloseableHttpClient httpClient;
//		try {
//			httpClient = HttpClients.custom()
//					.setConnectionManager(PoolingHttpClientConnectionManagerBuilder.create()
//							.setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
//									.setSslContext(SSLContextBuilder.create()
//											.loadTrustMaterial(TrustAllStrategy.INSTANCE).build())
//									.setHostnameVerifier(NoopHostnameVerifier.INSTANCE).build())
//							.build())
//					.build();
//			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//			requestFactory.setHttpClient(httpClient);
//			RestTemplate restTemplate = new RestTemplate(requestFactory);
//
//			return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).restOperations(restTemplate).build();
//		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}


	@Value("${cors.allowOrigins}")
	private String allowedOrigins;
	@Value("${cors.allowHeaders}")
	private String allowedHeaders;
	@Value("${cors.supportedMethods}")
	private String supportedMethods;
	@Value("${cors.allowCredentials}")
	private Boolean allowCredentials;

	/**
	 * Método que configura e define a política CORS para toda a aplicação.
	 *
	 * @return um {@link CorsConfigurationSource}
	 */

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
		configuration.setAllowedMethods(Arrays.asList(supportedMethods.split(",")));
		configuration.setAllowedHeaders(Arrays.asList(allowedHeaders.split(",")));
		configuration.setAllowCredentials(allowCredentials);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
