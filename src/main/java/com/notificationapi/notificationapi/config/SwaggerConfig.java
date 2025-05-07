package com.notificationapi.notificationapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        title = "PRZ Notification API",
        version = "1.0",
        description = "API for managing notifications",
        contact = @Contact(
            name = "PRZ",
            email = "rodrigodperozin@gmail.com",
            url = "https://prznotificationapi.com/"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8080", description = "Local"),
        @Server(url = "https://prznotificationapi.com", description = "Production"),
        @Server(url = "https://hom-prznotificationapi.com", description = "Development")
    }
)
public class SwaggerConfig {

}
