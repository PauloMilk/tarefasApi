package com.paulo.tarefas.api.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.paulo.tarefas.api.utils.JwtTokenUtil;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Profile("dev")
@EnableSwagger2
public class SwaggerConfig{

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.paulo.tarefas.api.controller"))
                .paths(regex("/api.*"))
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {

        @SuppressWarnings("rawtypes")
		ApiInfo apiInfo = new ApiInfo(
                "TASK API REST",
                "API REST de cadastro de tarefas.",
                "1.0",
                "Terms of Service",
                new Contact("Paulo Milk", "https://www.linkedin.com/in/pauloleitecosta/",
                        "pauloleitecosta14@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>()
        );

        return apiInfo;
    }

    @Bean
    public SecurityConfiguration secutiry() {
    	String token;
    	try {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername("admin@gmail.com");
			token = this.jwtTokenUtil.getToken(userDetails);
		} catch (Exception e) {
			token = "";
		}
    	return new SecurityConfiguration(null, null, null, null, "Bearer "+ token, ApiKeyVehicle.HEADER, "Authorization", ",");
    }
}
