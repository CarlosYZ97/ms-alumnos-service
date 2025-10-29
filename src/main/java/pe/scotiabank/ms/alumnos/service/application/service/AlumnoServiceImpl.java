package pe.scotiabank.ms.alumnos.service.application.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.scotiabank.ms.alumnos.service.domain.model.Alumno;
import pe.scotiabank.ms.alumnos.service.domain.repository.AlumnoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;

    @Override
    public Mono<Void> create(Alumno alumno) {
        log.info("[AlumnoServiceImpl][create] - INICIO: id= {}, nombre= {}, apellido= {}",
                alumno.getId(), alumno.getNombre(), alumno.getApellido());
        return alumnoRepository.create(alumno)
                .doOnSuccess(v -> log.info("[AlumnoServiceImpl][create] - FIN: id= {}, resultado= OK", alumno.getId()))
                .doOnError(ex -> log.error("[AlumnoServiceImpl][create] - ERROR: id= {}, mensaje= {}",
                        alumno.getId(), ex.getMessage()));
    }

    @Override
    public Flux<Alumno> findAllActivos() {
        log.info("[AlumnoServiceImpl][findAllActivos] - INICIO");
        return alumnoRepository.getAllActives()
                .doOnComplete(() -> log.info("[AlumnoServiceImpl][findAllActivos] - FIN: resultado= OK"))
                .doOnError(ex -> log.error("[AlumnoServiceImpl][findAllActivos] - ERROR: mensaje= {}", ex.getMessage()));
    }
}
