package pe.scotiabank.ms.alumnos.service.infrastructure.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("alumnos")
public class AlumnoEntity {

    @Id
    private Long id;
    private String nombre;
    private String apellido;
    private Integer estado;
    private Integer edad;

    @Version
    private Long version;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
