package pe.scotiabank.ms.alumnos.service.infrastructure.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.scotiabank.ms.alumnos.service.domain.enums.Estado;
import pe.scotiabank.ms.alumnos.service.domain.model.Alumno;
import pe.scotiabank.ms.alumnos.service.infrastructure.repository.entity.AlumnoEntity;
import pe.scotiabank.ms.alumnos.service.infrastructure.repository.mapper.AlumnoEntityMapperImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AlumnoRepositoryImplTest {

    @InjectMocks
    private AlumnoRepositoryImpl alumnoRepository;

    @Mock
    private SpringDataAlumnoRepository springDataAlumnoRepository;

    private AlumnoEntityMapperImpl mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mapper = new AlumnoEntityMapperImpl();
        alumnoRepository = new AlumnoRepositoryImpl(springDataAlumnoRepository, mapper);
    }

    @Test
    void create_success() {
        Alumno alumno = Alumno.builder()
                .id(1L)
                .nombre("Carlos")
                .apellido("Yunca")
                .estado(Estado.ACTIVO)
                .edad(25)
                .build();

        when(springDataAlumnoRepository.save(any(AlumnoEntity.class)))
                .thenReturn(Mono.just(mapper.toEntity(alumno)));

        Mono<Void> result = alumnoRepository.create(alumno);

        StepVerifier.create(result)
                .verifyComplete();

        verify(springDataAlumnoRepository, times(1)).save(any(AlumnoEntity.class));
    }

    @Test
    void getAllActives_success() {
        AlumnoEntity entity = AlumnoEntity.builder()
                .id(1L)
                .nombre("Carlos")
                .apellido("Yunca")
                .estado(Estado.ACTIVO.getCode())
                .edad(25)
                .build();

        when(springDataAlumnoRepository.findByEstado(Estado.ACTIVO.getCode()))
                .thenReturn(Flux.just(entity));

        Flux<Alumno> result = alumnoRepository.getAllActives();

        StepVerifier.create(result)
                .expectNextMatches(a -> a.getNombre().equals("Carlos") && a.getEstado() == Estado.ACTIVO)
                .verifyComplete();

        verify(springDataAlumnoRepository, times(1)).findByEstado(Estado.ACTIVO.getCode());
    }

    @Test
    void findById_success() {
        AlumnoEntity entity = AlumnoEntity.builder()
                .id(1L)
                .nombre("Ana")
                .apellido("Lopez")
                .estado(Estado.ACTIVO.getCode())
                .edad(22)
                .build();

        when(springDataAlumnoRepository.findById(1L))
                .thenReturn(Mono.just(entity));

        Mono<Alumno> result = alumnoRepository.findById(1L);

        StepVerifier.create(result)
                .expectNextMatches(a -> a.getNombre().equals("Ana") && a.getEstado() == Estado.ACTIVO)
                .verifyComplete();

        verify(springDataAlumnoRepository, times(1)).findById(1L);
    }

    @Test
    void update_success() {
        Alumno alumno = Alumno.builder()
                .id(1L)
                .nombre("Miguel")
                .apellido("Yunca")
                .estado(Estado.INACTIVO)
                .edad(30)
                .build();

        AlumnoEntity entity = mapper.toEntityForUpdate(alumno);

        when(springDataAlumnoRepository.save(any(AlumnoEntity.class)))
                .thenReturn(Mono.just(entity));

        Mono<Alumno> result = alumnoRepository.update(alumno);

        StepVerifier.create(result)
                .expectNextMatches(a -> a.getNombre().equals("Miguel")
                        && a.getEstado() == Estado.INACTIVO
                        && a.getEdad() == 30)
                .verifyComplete();

        verify(springDataAlumnoRepository, times(1)).save(any(AlumnoEntity.class));
    }

}
