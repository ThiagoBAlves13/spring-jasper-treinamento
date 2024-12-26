package com.treinamentoJasper.spring_jasper_treinamento.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;


@Configuration
public class SwaggerConfig {

	/**
	 * 
	 * Método que define a configuração do Swagger - OpenApi (sem autorização)
	 * 
	 * @return configuração OpenAPI
	 */
	
	@Bean
	public OpenAPI swaggerAPI() {
		return new OpenAPI()
				.info(new Info().title("TEMPLATE").version("1.0")
				.description("Documentação da API (backend) do projeto Demo. Abaixo são "+
								"listados todos os serviços REST utilizados pelo frontend da aplicação."));
	}


//	@Value("${keycloak.auth-server-url}")
//	private String AUTH_SERVER;
//
//	@Value("${keycloak.resource}")
//	private String CLIENT_ID;
//
//	@Value("${keycloak.realm}")
//	private String REALM;
//	
//	@Value("${demo.version}")
//	private String API_VERSION;
//	
//	/**
//	 * 
//	 * Método que define a configuração do Swagger - OpenApi (com autorização)
//	 * 
//	 * @return configuração OpenAPI
//	 */
//	
//	@Bean
//	public OpenAPI swaggerAPI() {
//		final String securitySchemeName = "JWT";
//		return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
//				.components(new Components().addSecuritySchemes(securitySchemeName, new SecurityScheme()
//						.name(securitySchemeName)
//						.type(SecurityScheme.Type.OAUTH2)
//						.flows(new OAuthFlows().authorizationCode(new OAuthFlow()
//								.authorizationUrl(AUTH_SERVER + "/realms/" + REALM + "/protocol/openid-connect/auth")
//								.refreshUrl(AUTH_SERVER + "/realms/" + REALM + "/protocol/openid-connect/token")
//								.tokenUrl(AUTH_SERVER + "/realms/" + REALM + "/protocol/openid-connect/token")))))
//				.info(new Info().title("TEMPLATE").version("1.0")
//				.description("Documentação da API (backend) do projeto Demo. Abaixo são "+
//								"listados todos os serviços REST utilizados pelo frontend da aplicação."));
//	}


}
