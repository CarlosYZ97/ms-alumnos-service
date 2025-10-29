package pe.scotiabank.ms.alumnos.service.infrastructure.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pe.scotiabank.ms.alumnos.service.infrastructure.repository.SpringDataAlumnoRepository;
import pe.scotiabank.ms.alumnos.service.infrastructure.repository.SpringDataAlumnoRepositoryFake;

@Configuration
public class AlumnoRepositoryFakeConfig {

    @Bean
    @Primary
    public SpringDataAlumnoRepository springDataAlumnoRepositoryFake() {
        return new SpringDataAlumnoRepositoryFake();
    }

}
