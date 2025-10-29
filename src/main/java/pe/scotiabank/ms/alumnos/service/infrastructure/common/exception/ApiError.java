package pe.scotiabank.ms.alumnos.service.infrastructure.common.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {
    private String message;
    private String codigo;
    private String path;
}
