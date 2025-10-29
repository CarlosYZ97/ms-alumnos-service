package pe.scotiabank.ms.alumnos.service.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.scotiabank.ms.alumnos.service.application.service.AlumnoServiceImpl;
import pe.scotiabank.ms.alumnos.service.domain.enums.Estado;
import pe.scotiabank.ms.alumnos.service.domain.model.Alumno;
import pe.scotiabank.ms.alumnos.service.domain.repository.AlumnoRepository;
import pe.scotiabank.ms.alumnos.service.infrastructure.common.exception.BusinessException;
import pe.scotiabank.ms.alumnos.service.infrastructure.common.exception.ErrorCode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AlumnoServiceImplTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    private Alumno alumno;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        alumno = Alumno.builder()
                .id(1)
                .nombre("Carlos")
                .apellido("Yunca")
                .estado(Estado.ACTIVO)
                .edad(25)
                .build();
    }

    @Test
    void create_success() {
        when(alumnoRepository.create(any(Alumno.class))).thenReturn(Mono.empty());

        Mono<Void> result = alumnoService.create(alumno);

        StepVerifier.create(result)
                .verifyComplete();

        verify(alumnoRepository, times(1)).create(alumno);
    }

    @Test
    void create_duplicateId() {
        when(alumnoRepository.create(any(Alumno.class)))
                .thenReturn(Mono.error(new BusinessException(ErrorCode.DUPLICATE_ID)));

        Mono<Void> result = alumnoService.create(alumno);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof BusinessException
                        && ((BusinessException) throwable).getCode().equals(ErrorCode.DUPLICATE_ID.getCode()))
                .verify();

        verify(alumnoRepository, times(1)).create(alumno);
    }

    @Test
    void findAllActivos_success() {
        when(alumnoRepository.getAllActives()).thenReturn(Flux.just(alumno));

        Flux<Alumno> result = alumnoService.findAllActivos();

        StepVerifier.create(result)
                .expectNextMatches(a -> a.getId().equals(1) && a.getEstado() == Estado.ACTIVO)
                .verifyComplete();

        verify(alumnoRepository, times(1)).getAllActives();
    }

    @Test
    void findAllActivos_empty() {
        when(alumnoRepository.getAllActives()).thenReturn(Flux.empty());

        Flux<Alumno> result = alumnoService.findAllActivos();

        StepVerifier.create(result)
                .verifyComplete();

        verify(alumnoRepository, times(1)).getAllActives();
    }

}
