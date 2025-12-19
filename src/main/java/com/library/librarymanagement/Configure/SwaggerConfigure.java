package com.library.librarymanagement.Configure;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfigure {

    @Bean
    public OpenAPI libraryOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Library Management System API")
                        .description("Spring Boot REST APIs for Library Management")
                        .version("1.0"));
    }
}
