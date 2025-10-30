package pe.scotiabank.ms.alumnos.service.application.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.scotiabank.ms.alumnos.service.domain.enums.Estado;
import pe.scotiabank.ms.alumnos.service.domain.model.Alumno;
import pe.scotiabank.ms.alumnos.service.domain.repository.AlumnoRepository;
import pe.scotiabank.ms.alumnos.service.infrastructure.common.exception.BusinessException;
import pe.scotiabank.ms.alumnos.service.infrastructure.common.exception.ErrorCode;
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
        alumno.setEstado(Estado.ACTIVO);
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

    @Override
    public Mono<Alumno> update(Long id, Alumno alumno) {
        return alumnoRepository.findById(id)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorCode.ALUMNO_NOT_FOUND)))
                .flatMap(existing -> {
                    existing.setNombre(alumno.getNombre());
                    existing.setApellido(alumno.getApellido());
                    existing.setEdad(alumno.getEdad());
                    existing.setEstado(alumno.getEstado());
                    return alumnoRepository.update(existing);
                });
    }
}
