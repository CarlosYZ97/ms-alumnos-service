package pe.scotiabank.ms.alumnos.service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.scotiabank.ms.alumnos.service.domain.enums.Estado;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alumno {

    private Long id;
    private String nombre;
    private String apellido;
    private Estado estado;
    private Integer edad;
    private Long version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
