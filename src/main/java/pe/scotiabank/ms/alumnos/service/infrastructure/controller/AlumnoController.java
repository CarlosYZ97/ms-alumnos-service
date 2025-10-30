package pe.scotiabank.ms.alumnos.service.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.scotiabank.ms.alumnos.service.application.service.AlumnoService;
import pe.scotiabank.ms.alumnos.service.infrastructure.controller.dto.AlumnoCreateRequestDto;
import pe.scotiabank.ms.alumnos.service.infrastructure.controller.dto.AlumnoResponseDto;
import pe.scotiabank.ms.alumnos.service.infrastructure.controller.dto.AlumnoUpdateRequestDto;
import pe.scotiabank.ms.alumnos.service.infrastructure.controller.mapper.AlumnoDtoMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/alumnos")
@Tag(name = "alumno-controller", description = "Endpoints para la gestión de alumnos")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoService alumnoService;
    private final AlumnoDtoMapper alumnoDtoMapper;

    @Operation(
            summary = "Registrar un nuevo alumno",
            description = "Crea un nuevo alumno en el sistema con estado ACTIVO.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Alumno creado correctamente"),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> create(
            @Valid @RequestBody
            @Parameter(description = "Datos del alumno a registrar", required = true)
            AlumnoCreateRequestDto requestDto) {
        return alumnoService.create(alumnoDtoMapper.toModel(requestDto));
    }

    @Operation(
            summary = "Actualizar un alumno existente",
            description = "Permite modificar los datos de un alumno registrado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Alumno actualizado correctamente",
                            content = @Content(schema = @Schema(implementation = AlumnoResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Alumno no encontrado", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
            }
    )
    @PutMapping("/{id}")
    public Mono<AlumnoResponseDto> update(
                                          @Parameter(description = "ID del alumno a actualizar", required = true)
                                          @PathVariable Long id,
                                          @Valid @RequestBody
                                          @Parameter(description = "Datos a actualizar", required = true)
                                          AlumnoUpdateRequestDto updateRequestDto) {
        return alumnoService.update(id,alumnoDtoMapper.toModel(updateRequestDto))
                .map(alumnoDtoMapper::toResponseDto);
    }

    @Operation(
            summary = "Listar alumnos activos",
            description = "Devuelve un listado de todos los alumnos con estado ACTIVO.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                            content = @Content(schema = @Schema(implementation = AlumnoResponseDto.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
            }
    )
    @GetMapping
    public Flux<AlumnoResponseDto> getAllActives() {
        return alumnoService.findAllActivos()
                .map(alumnoDtoMapper::toResponseDto);
    }
}
