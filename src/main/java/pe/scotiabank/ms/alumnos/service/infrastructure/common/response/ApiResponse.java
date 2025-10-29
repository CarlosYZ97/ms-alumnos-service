package pe.scotiabank.ms.alumnos.service.infrastructure.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private T data;
}
