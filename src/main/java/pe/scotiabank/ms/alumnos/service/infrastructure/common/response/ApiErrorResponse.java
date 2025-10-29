package pe.scotiabank.ms.alumnos.service.infrastructure.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pe.scotiabank.ms.alumnos.service.infrastructure.common.exception.ApiError;

@Getter
@AllArgsConstructor
public class ApiErrorResponse {

    private final ApiError error;

}
