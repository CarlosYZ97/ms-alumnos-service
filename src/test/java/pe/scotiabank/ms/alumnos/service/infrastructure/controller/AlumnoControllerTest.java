package pe.scotiabank.ms.alumnos.service.infrastructure.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import pe.scotiabank.ms.alumnos.service.application.service.AlumnoService;
import pe.scotiabank.ms.alumnos.service.domain.enums.Estado;
import pe.scotiabank.ms.alumnos.service.domain.model.Alumno;
import pe.scotiabank.ms.alumnos.service.infrastructure.common.exception.BusinessException;
import pe.scotiabank.ms.alumnos.service.infrastructure.common.exception.ErrorCode;
import pe.scotiabank.ms.alumnos.service.infrastructure.controller.dto.AlumnoCreateRequestDto;
import pe.scotiabank.ms.alumnos.service.infrastructure.controller.dto.AlumnoResponseDto;
import pe.scotiabank.ms.alumnos.service.infrastructure.controller.dto.AlumnoUpdateRequestDto;
import pe.scotiabank.ms.alumnos.service.infrastructure.controller.mapper.AlumnoDtoMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = AlumnoController.class)
public class AlumnoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private AlumnoService alumnoService;

    @MockitoBean
    private AlumnoDtoMapper alumnoDtoMapper;

    private Alumno alumno;
    private AlumnoCreateRequestDto requestDto;

    @BeforeEach
    void setUp() {
        alumno = Alumno.builder()
                .id(1L)
                .nombre("Carlos")
                .apellido("Yunca")
                .estado(Estado.ACTIVO)
                .edad(25)
                .build();

        requestDto = new AlumnoCreateRequestDto("Carlos", "Yunca", 25);
    }

    @Test
    void create_success() {
        when(alumnoDtoMapper.toModel(any(AlumnoCreateRequestDto.class))).thenReturn(alumno);
        when(alumnoService.create(any(Alumno.class))).thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/api/v1/alumnos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().isEmpty();
    }

    @Test
    void getAllActives_success() {
        when(alumnoService.findAllActivos()).thenReturn(Flux.just(alumno));
        when(alumnoDtoMapper.toResponseDto(alumno)).thenAnswer(inv ->
                new AlumnoResponseDto(
                        alumno.getId(), alumno.getNombre(), alumno.getApellido(),
                        alumno.getEstado(), alumno.getEdad()
                )
        );

        webTestClient.get()
                .uri("/api/v1/alumnos")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data[0].id").isEqualTo(1)
                .jsonPath("$.data[0].nombre").isEqualTo("Carlos")
                .jsonPath("$.data[0].apellido").isEqualTo("Yunca")
                .jsonPath("$.data[0].estado").isEqualTo("ACTIVO")
                .jsonPath("$.data[0].edad").isEqualTo(25);
    }

    @Test
    void create_validationError() {
        AlumnoCreateRequestDto invalidRequest = new AlumnoCreateRequestDto("", "", 25);

        webTestClient.post()
                .uri("/api/v1/alumnos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.error.codigo").isEqualTo("VALIDATION_ERROR");
    }

    @Test
    void update_success() {
        AlumnoUpdateRequestDto updateRequest = new AlumnoUpdateRequestDto("Miguel", "Yunca", Estado.INACTIVO, 30);
        Alumno updated = Alumno.builder()
                .id(1L)
                .nombre("Miguel")
                .apellido("Yunca")
                .estado(Estado.INACTIVO)
                .edad(30)
                .build();

        when(alumnoDtoMapper.toModel(any(AlumnoUpdateRequestDto.class))).thenReturn(updated);
        when(alumnoService.update(1L, updated)).thenReturn(Mono.just(updated));
        when(alumnoDtoMapper.toResponseDto(updated)).thenReturn(
                new AlumnoResponseDto(1L, "Miguel", "Yunca", Estado.INACTIVO, 30)
        );

        webTestClient.put()
                .uri("/api/v1/alumnos/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.id").isEqualTo(1)
                .jsonPath("$.data.nombre").isEqualTo("Miguel")
                .jsonPath("$.data.apellido").isEqualTo("Yunca")
                .jsonPath("$.data.estado").isEqualTo("INACTIVO")
                .jsonPath("$.data.edad").isEqualTo(30);
    }

    @Test
    void update_notFound() {
        AlumnoUpdateRequestDto updateRequest = new AlumnoUpdateRequestDto("Juan", "Perez", Estado.ACTIVO, 28);

        when(alumnoDtoMapper.toModel(any(AlumnoUpdateRequestDto.class)))
                .thenReturn(Alumno.builder().build());
        when(alumnoService.update(any(Long.class), any(Alumno.class)))
                .thenReturn(Mono.error(new BusinessException(ErrorCode.ALUMNO_NOT_FOUND)));

        webTestClient.put()
                .uri("/api/v1/alumnos/{id}", 99L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.error.codigo").isEqualTo("ALUMNO_NOT_FOUND")
                .jsonPath("$.error.message").isEqualTo("Alumno no encontrado");
    }

}
