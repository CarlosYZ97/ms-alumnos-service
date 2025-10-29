package pe.scotiabank.ms.alumnos.service.domain.repository;

import pe.scotiabank.ms.alumnos.service.domain.model.Alumno;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlumnoRepository {
    Mono<Void> create(Alumno alumno);

    Flux<Alumno> getAllActives();
}
