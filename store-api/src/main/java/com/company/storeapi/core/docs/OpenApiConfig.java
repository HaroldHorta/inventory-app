package com.company.storeapi.core.docs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Open api config.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Custom open api data open api.
     *
     * @return the open api
     */
    @Bean
    public OpenAPI customOpenAPIData() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Inventory API")
                        .description("Esta es la especificación de la REST API de inventario para sistema de ventas.")
                        .version("0.0.1"));
    }
}
