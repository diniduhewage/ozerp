package com.onenzero.ozerp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class LocalizationConfig {
	
	  @Bean
	    public LocaleResolver localeResolver(){
	        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
	        sessionLocaleResolver.setDefaultLocale(Locale.US);
	        return sessionLocaleResolver;


	    }

	    @Bean
	    public ResourceBundleMessageSource messageSource(){
	        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
	        resourceBundleMessageSource.setBasename("language");
	        resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
	        return resourceBundleMessageSource;
	    }

}