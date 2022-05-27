package com.alkemy.ong.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(InputStream.class, ResponseEntity.class, ResponseStatusException.class, StackTraceElement.class, Throwable.class, ResponseEntity.class)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(Predicates.or(RequestHandlerSelectors.basePackage("com.alkemy.ong.auth.controller")
                        ,(RequestHandlerSelectors.basePackage("com.alkemy.ong.controller"))))
                //.paths(postPaths())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());

    }


    //When add a route MUST BE with format:
    // (/route)|(/route/.*)

    //TODO when documentation be implemented, delete this example route
    private Predicate<String> postPaths() {
        String routes = "(/auth)|(/auth/.*)|(/news)|(/news/.*)|(/categories)|(/categories/.*)|(/testimonials)|(/testimonials/.*)";
        Predicate<String> retorno = a -> a.matches(routes);
        return retorno;
    }
    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }


    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Somos Mas API",
                "ONG Somos Mas API Description",
                "1.0",
                "http://fundacionsomosmas.com/terms",
                new Contact("Fundación Somos Más", "https://fundacionsomosmas.com", "somosfundacionmas@gmail.com"),
                "LICENSE",
                "LICENSE URL",
                Collections.emptyList()
        );
    }
}
