package com.testsquad.crud.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Extension Platform Registration API",
                version = "v1",
                description = "Staff self-registration API for extension project submission and proposal review.",
                contact = @Contact(name = "Test Squad"),
                license = @License(name = "Academic use")
        )
)
public class OpenApiConfig {
}
