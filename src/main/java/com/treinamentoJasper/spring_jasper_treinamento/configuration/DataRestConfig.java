package com.treinamentoJasper.spring_jasper_treinamento.configuration;

import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ExposureConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@Component
public class DataRestConfig implements RepositoryRestConfigurer {

	@Value("${cors.allowOrigins}")
	private String allowedOrigins;
	@Value("${cors.allowHeaders}")
	private String allowedHeaders;
	@Value("${cors.allowCredentials}")
	private Boolean allowCredentials;
	@Value("${cors.supportedMethods}")
	private String supportedMethods;
		
	/**
	 * Método que (re)define configurações do Spring DataREST, tanto globais quanto para domínios/classes específicas.
	 * 
	 * @param restConfig objeto auto-injetado {@link RepositoryRestConfiguration}
	 */
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration restConfig, CorsRegistry cors) {

		CorsRegistration reg = cors.addMapping("/error");
	    reg.allowedOrigins(allowedOrigins.split(","));
	    reg.allowedHeaders(allowedHeaders.split(","));
	    reg.allowCredentials(allowCredentials);
	    reg.allowedMethods(supportedMethods.split(","));
		
		restConfig.disableDefaultExposure();
		restConfig.setReturnBodyForPutAndPost(false);

		ExposureConfiguration exposureConfig = restConfig.getExposureConfiguration();

		exposureConfig.disablePutForCreation();
		exposureConfig.disablePutOnItemResources();
		exposureConfig.disablePatchOnItemResources();
		exposureConfig.withCollectionExposure((metadata, httpMethods) ->
			httpMethods.disable(HttpMethod.POST));
		
		
		final ClassPathScanningCandidateComponentProvider provider =
				new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
		final Set<BeanDefinition> beans = provider
				.findCandidateComponents("br.mil.marinha.casnav.example.api.demo.persistence.model");
		for (BeanDefinition bean : beans) {
			Class<?> idExposedClasses = null;
			try {
				idExposedClasses = Class.forName(bean.getBeanClassName());
				restConfig.exposeIdsFor(Class.forName(idExposedClasses.getName()));
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Falha ao expor campo `id` devido a", e);
			}
		}
	}

}