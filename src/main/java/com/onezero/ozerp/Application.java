package com.onezero.ozerp;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableJpaAuditing
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedOrigins("*")
                        .allowedHeaders("*");
            }
        };
    }

    @Bean
    public GroupedOpenApi geoDataAdminApi() {
        return GroupedOpenApi.builder()
                .group("geo-data-admin-api")
                .pathsToMatch("/api/v1/geo-data/**")
                .build();
    }

    @Bean
    public GroupedOpenApi accessManagerAdminApi() {
        return GroupedOpenApi.builder()
                .group("access-manager-api")
                .pathsToMatch("/api/v1/**")
                .build();
    }


    @Bean
    public OpenAPI accessManagerAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearer-jwt",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                //  .addServersItem(new Server().url("/access-manager-api"))
                .info(new Info().title("Rental Service")
                        .description("Rental Service Application")
                        .version("v1.0.0")
                        .license(new License().name("OnesNZeros").url("https://onesnzeros.com/")))
                .externalDocs(new ExternalDocumentation()
                        .description("Rental Service Documentation")
                        .url("https://onesnzeros/REST+API+Services"));
    }

}
