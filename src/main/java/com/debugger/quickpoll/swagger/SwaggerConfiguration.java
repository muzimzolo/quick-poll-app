package com.debugger.quickpoll.swagger;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {
	
    @Bean
    // http://localhost:8080/swagger-ui/
    public Docket apiV1() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/v1/*.*"))
                .build()
                .apiInfo(apiInfo("v1"))
                .groupName("v1")
                .useDefaultResponseMessages(false);
        // docket.useDefaultResponseMessages(false);
        // (PathSelectors.regex("/polls/*.*|/votes/*.*|/computeresult/*.*")));
    }
    private ApiInfo apiInfo(String version) {
        return new ApiInfo(
                "QuickPoll REST API",
                "QuickPoll Api for creating and managing polls",
                "https://github.com/muzimzolo/quick-poll-app",
                "Terms of service",
                new Contact("Muzi Mzolo", "https://github.com/muzimzolo/quick-poll-app", "meneer.mzolo@gmail.com"),
                "MIT License", "http://opensource.org/licenses/MIT", Collections.emptyList());
        /*
         * springfox.documentation.service.ApiInfo.ApiInfo
         * (String title, String description, String version, String termsOfServiceUrl, 
         * Contact contact, String license, String licenseUrl, 
         * Collection<VendorExtension> vendorExtensions)
         * 
         * http://localhost:8080/v3/api-docs
         */
    }
}