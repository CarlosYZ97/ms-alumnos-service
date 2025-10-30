package pe.scotiabank.ms.alumnos.service.infrastructure.common.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        .info(new Info()
                                .title("MS-Alumnos API")
                                .version("1.0")
                                .description("API Reactiva para la gestión de alumnos"));
        }

}
