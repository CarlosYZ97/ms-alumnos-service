package pe.scotiabank.ms.alumnos.service.infrastructure.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pe.scotiabank.ms.alumnos.service.domain.enums.Estado;
import pe.scotiabank.ms.alumnos.service.domain.model.Alumno;
import pe.scotiabank.ms.alumnos.service.domain.repository.AlumnoRepository;
import pe.scotiabank.ms.alumnos.service.infrastructure.repository.mapper.AlumnoEntityMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class AlumnoRepositoryImpl implements AlumnoRepository {

    private final SpringDataAlumnoRepository springDataAlumnoRepository;
    private final AlumnoEntityMapper alumnoEntityMapper;

    @Override
    public Mono<Void> create(Alumno alumno) {
        return springDataAlumnoRepository
                .save(alumnoEntityMapper.toEntity(alumno))
                .then();
    }

    @Override
    public Flux<Alumno> getAllActives() {
        return springDataAlumnoRepository.findByEstado(Estado.ACTIVO.getCode())
                .map(alumnoEntityMapper::toModel);
    }

    @Override
    public Mono<Alumno> findById(Long id) {
        return springDataAlumnoRepository.findById(id)
                .map(alumnoEntityMapper::toModel);
    }

    @Override
    public Mono<Alumno> update(Alumno alumno) {
        return springDataAlumnoRepository
                .save(alumnoEntityMapper.toEntityForUpdate(alumno))
                .map(alumnoEntityMapper::toModel);
    }
}
