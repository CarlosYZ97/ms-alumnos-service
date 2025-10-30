package pe.scotiabank.ms.alumnos.service.infrastructure.controller.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.scotiabank.ms.alumnos.service.domain.enums.Estado;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoUpdateRequestDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe exceder los 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no debe exceder los 100 caracteres")
    private String apellido;

    @NotNull(message = "El estado es obligatorio (INACTIVO, ACTIVO)")
    private Estado estado;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 1, message = "La edad mínima es 1")
    @Max(value = 120, message = "La edad máxima es 120")
    private Integer edad;

}
