package pe.scotiabank.ms.alumnos.service.application.service;

import pe.scotiabank.ms.alumnos.service.domain.model.Alumno;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlumnoService {

    Mono<Void> create (Alumno alumno);

    Flux<Alumno> findAllActivos();

    Mono<Alumno> update (Long id, Alumno alumno);

}
