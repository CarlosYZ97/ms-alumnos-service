package pe.scotiabank.ms.alumnos.service.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Estado {

    INACTIVO(0, "INACTIVO"),
    ACTIVO(1, "ACTIVO");

    private final int code;
    private final String value;

    Estado(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Estado fromValue(String value) {
        for (Estado estado : values()) {
            if (estado.value.equalsIgnoreCase(value)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado inválido: " + value);
    }

    public static Estado fromCode(int code) {
        for (Estado estado : values()) {
            if (estado.code == code) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Código de estado inválido: " + code);
    }

}
