package pe.scotiabank.ms.alumnos.service.infrastructure.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.scotiabank.ms.alumnos.service.domain.enums.Estado;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlumnoResponseDto {

    private Long id;
    private String nombre;
    private String apellido;
    private Estado estado;
    private Integer edad;
}
