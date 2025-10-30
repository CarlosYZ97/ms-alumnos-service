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
                .id(1L)
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
    void findAllActivos_success() {
        when(alumnoRepository.getAllActives()).thenReturn(Flux.just(alumno));

        Flux<Alumno> result = alumnoService.findAllActivos();

        StepVerifier.create(result)
                .expectNextMatches(a -> a.getId().equals(1L) && a.getEstado() == Estado.ACTIVO)
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

    @Test
    void update_success() {
        Alumno existing = Alumno.builder()
                .id(1L)
                .nombre("Carlos")
                .apellido("Yunca")
                .estado(Estado.ACTIVO)
                .edad(25)
                .build();

        Alumno request = Alumno.builder()
                .nombre("Miguel")
                .apellido("Yunca")
                .estado(Estado.INACTIVO)
                .edad(30)
                .build();

        when(alumnoRepository.findById(1L)).thenReturn(Mono.just(existing));
        when(alumnoRepository.update(any(Alumno.class)))
                .thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        Mono<Alumno> result = alumnoService.update(1L, request);

        StepVerifier.create(result)
                .assertNext(a -> {
                    org.assertj.core.api.Assertions.assertThat(a.getNombre()).isEqualTo("Miguel");
                    org.assertj.core.api.Assertions.assertThat(a.getEstado()).isEqualTo(Estado.INACTIVO);
                    org.assertj.core.api.Assertions.assertThat(a.getEdad()).isEqualTo(30);
                })
                .verifyComplete();

        verify(alumnoRepository, times(1)).findById(1L);
        verify(alumnoRepository, times(1)).update(any(Alumno.class));
    }

    @Test
    void update_notFound() {
        when(alumnoRepository.findById(1L)).thenReturn(Mono.empty());

        Mono<Alumno> result = alumnoService.update(1L, alumno);

        StepVerifier.create(result)
                .expectErrorMatches(ex ->
                        ex instanceof BusinessException &&
                                ((BusinessException) ex).getCode().equals(ErrorCode.ALUMNO_NOT_FOUND.getCode()))
                .verify();

        verify(alumnoRepository, times(1)).findById(1L);
        verify(alumnoRepository, never()).update(any());
    }

}
