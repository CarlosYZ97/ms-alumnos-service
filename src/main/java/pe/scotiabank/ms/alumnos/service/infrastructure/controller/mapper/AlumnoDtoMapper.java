package pe.scotiabank.ms.alumnos.service.infrastructure.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.scotiabank.ms.alumnos.service.domain.enums.Estado;
import pe.scotiabank.ms.alumnos.service.domain.model.Alumno;
import pe.scotiabank.ms.alumnos.service.infrastructure.controller.dto.AlumnoRequestDto;
import pe.scotiabank.ms.alumnos.service.infrastructure.controller.dto.AlumnoResponseDto;

@Mapper(componentModel = "spring")
public interface AlumnoDtoMapper {

    Alumno toModel(AlumnoRequestDto dto);

    AlumnoResponseDto toResponseDto(Alumno model);
}
