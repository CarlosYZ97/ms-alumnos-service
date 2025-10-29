package pe.scotiabank.ms.alumnos.service.infrastructure.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    BUSINESS_ERROR("BUSINESS_ERROR", "No se pudo realizar la operación de negocio"),
    DUPLICATE_ID("DUPLICATE_ID", "El alumno con este ID ya existe"),
    VALIDATION_ERROR("VALIDATION_ERROR", "Error de validación en los datos"),
    GENERIC_ERROR("GENERIC_ERROR", "Ha ocurrido un error inesperado");

    private final String code;
    private final String defaultMessage;

    ErrorCode(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

}
