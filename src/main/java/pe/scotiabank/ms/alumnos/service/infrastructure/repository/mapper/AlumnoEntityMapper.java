package pe.scotiabank.ms.alumnos.service.infrastructure.repository.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.scotiabank.ms.alumnos.service.domain.model.Alumno;
import pe.scotiabank.ms.alumnos.service.infrastructure.repository.entity.AlumnoEntity;

@Mapper(componentModel = "spring")
public interface AlumnoEntityMapper {

    @Mapping(target = "estado", expression = "java(alumno.getEstado().getCode())")
    AlumnoEntity toEntity(Alumno alumno);

    @Mapping(
            target = "estado",
            expression = "java(pe.scotiabank.ms.alumnos.service.domain.enums.Estado.fromCode(alumnoEntity.getEstado()))"
    )
    Alumno toModel(AlumnoEntity alumnoEntity);

}
