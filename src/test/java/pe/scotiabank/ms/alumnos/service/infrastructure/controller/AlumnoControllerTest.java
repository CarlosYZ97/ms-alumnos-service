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
import pe.scotiabank.ms.alumnos.service.infrastructure.controller.dto.AlumnoRequestDto;
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
    private AlumnoRequestDto requestDto;

    @BeforeEach
    void setUp() {
        alumno = Alumno.builder()
                .id(1)
                .nombre("Carlos")
                .apellido("Yunca")
                .estado(Estado.ACTIVO)
                .edad(25)
                .build();

        requestDto = new AlumnoRequestDto(1, "Carlos", "Yunca", Estado.ACTIVO, 25);
    }

    @Test
    void create_success() {
        when(alumnoDtoMapper.toModel(any(AlumnoRequestDto.class))).thenReturn(alumno);
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
                new pe.scotiabank.ms.alumnos.service.infrastructure.controller.dto.AlumnoResponseDto(
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
        AlumnoRequestDto invalidRequest = new AlumnoRequestDto(null, "", "", null, 25);

        webTestClient.post()
                .uri("/api/v1/alumnos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.error.codigo").isEqualTo("VALIDATION_ERROR");
    }

}
