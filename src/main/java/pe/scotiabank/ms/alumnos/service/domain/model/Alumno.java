package pe.scotiabank.ms.alumnos.service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.scotiabank.ms.alumnos.service.domain.enums.Estado;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alumno {

    private Integer id;
    private String nombre;
    private String apellido;
    private Estado estado;
    private Integer edad;

}
