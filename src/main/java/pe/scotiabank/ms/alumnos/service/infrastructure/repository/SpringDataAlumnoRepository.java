package pe.scotiabank.ms.alumnos.service.infrastructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.scotiabank.ms.alumnos.service.infrastructure.repository.entity.AlumnoEntity;
import reactor.core.publisher.Flux;

public interface SpringDataAlumnoRepository  extends ReactiveCrudRepository<AlumnoEntity, Long> {

    Flux<AlumnoEntity> findByEstado(Integer estado);

}
