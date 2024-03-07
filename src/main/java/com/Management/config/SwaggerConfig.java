package com.Management.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info =  @Info (
                contact = @Contact(
                        name = "Gauri Tambakhe",
                        url = "WeeTechSolution.com",
                        email = "tambakhegauri@gmail"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "swagger.org"
                ),
                version = "1.0",
                title = "Comp-Employee-Management",
                description = "OpenApi documentation for the Employee Management Project",
                termsOfService = "Term And Service"
                ),
        servers = {
                @Server(
                        description = "local EVN",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "private EVN",
                        url = "WeeTechSolution.com"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
         name = "bearerAuth",
        description = "Bearer token For Authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class SwaggerConfig {
}