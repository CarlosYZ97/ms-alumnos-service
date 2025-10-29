package pe.scotiabank.ms.alumnos.service.infrastructure.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.scotiabank.ms.alumnos.service.application.service.AlumnoService;
import pe.scotiabank.ms.alumnos.service.infrastructure.controller.dto.AlumnoRequestDto;
import pe.scotiabank.ms.alumnos.service.infrastructure.controller.dto.AlumnoResponseDto;
import pe.scotiabank.ms.alumnos.service.infrastructure.controller.mapper.AlumnoDtoMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/alumnos")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoService alumnoService;
    private final AlumnoDtoMapper alumnoDtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> create(@Valid @RequestBody AlumnoRequestDto requestDto) {
        return alumnoService.create(alumnoDtoMapper.toModel(requestDto));
    }

    @GetMapping
    public Flux<AlumnoResponseDto> getAllActives() {
        return alumnoService.findAllActivos()
                .map(alumnoDtoMapper::toResponseDto);
    }
}
