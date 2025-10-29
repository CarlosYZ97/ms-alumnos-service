package pe.scotiabank.ms.alumnos.service.infrastructure.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.scotiabank.ms.alumnos.service.domain.enums.Estado;
import pe.scotiabank.ms.alumnos.service.domain.model.Alumno;
import pe.scotiabank.ms.alumnos.service.infrastructure.common.exception.BusinessException;
import pe.scotiabank.ms.alumnos.service.infrastructure.common.exception.ErrorCode;
import pe.scotiabank.ms.alumnos.service.infrastructure.repository.mapper.AlumnoEntityMapperImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlumnoRepositoryImplTest {

    private AlumnoRepositoryImpl alumnoRepository;

    @BeforeEach
    void setUp() {
        SpringDataAlumnoRepositoryFake fakeRepo = new SpringDataAlumnoRepositoryFake();
        AlumnoEntityMapperImpl mapper = new AlumnoEntityMapperImpl();
        alumnoRepository = new AlumnoRepositoryImpl(fakeRepo, mapper);
    }

    @Test
    void create_success() {
        Alumno alumno = Alumno.builder()
                .id(1)
                .nombre("Carlos")
                .apellido("Yunca")
                .estado(Estado.ACTIVO)
                .edad(20)
                .build();

        Mono<Void> result = alumnoRepository.create(alumno);

        StepVerifier.create(result)
                .verifyComplete();

        Flux<Alumno> activos = alumnoRepository.getAllActives();

        StepVerifier.create(activos)
                .expectNextMatches(a -> a.getId().equals(1) &&
                        a.getNombre().equals("Carlos") &&
                        a.getEstado() == Estado.ACTIVO)
                .verifyComplete();
    }

    @Test
    void create_duplicateId_shouldFail() {
        Alumno alumno1 = Alumno.builder()
                .id(1).nombre("Carlos").apellido("Yunca").estado(Estado.ACTIVO).edad(20).build();
        Alumno alumno2 = Alumno.builder()
                .id(1).nombre("Luis").apellido("Perez").estado(Estado.INACTIVO).edad(30).build();

        StepVerifier.create(alumnoRepository.create(alumno1))
                .verifyComplete();

        StepVerifier.create(alumnoRepository.create(alumno2))
                .expectErrorSatisfies(error -> {
                    assertTrue(error instanceof BusinessException);
                    assertEquals(ErrorCode.DUPLICATE_ID.getCode(), ((BusinessException) error).getCode());
                })
                .verify();
    }

    @Test
    void getAllActives_onlyActiveReturned() {
        Alumno activo = Alumno.builder()
                .id(1).nombre("Ana").apellido("Lopez").estado(Estado.ACTIVO).edad(18).build();
        Alumno inactivo = Alumno.builder()
                .id(2).nombre("Pepe").apellido("Ramirez").estado(Estado.INACTIVO).edad(19).build();

        StepVerifier.create(alumnoRepository.create(activo)).verifyComplete();
        StepVerifier.create(alumnoRepository.create(inactivo)).verifyComplete();

        Flux<Alumno> result = alumnoRepository.getAllActives();

        StepVerifier.create(result)
                .expectNextMatches(a -> a.getId().equals(1) && a.getEstado() == Estado.ACTIVO)
                .verifyComplete();
    }

}
