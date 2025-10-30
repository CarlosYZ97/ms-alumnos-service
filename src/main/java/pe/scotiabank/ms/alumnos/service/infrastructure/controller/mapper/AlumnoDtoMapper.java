package pe.scotiabank.ms.alumnos.service.infrastructure.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.scotiabank.ms.alumnos.service.domain.model.Alumno;
import pe.scotiabank.ms.alumnos.service.infrastructure.controller.dto.AlumnoCreateRequestDto;
import pe.scotiabank.ms.alumnos.service.infrastructure.controller.dto.AlumnoResponseDto;
import pe.scotiabank.ms.alumnos.service.infrastructure.controller.dto.AlumnoUpdateRequestDto;

@Mapper(componentModel = "spring")
public interface AlumnoDtoMapper {

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Alumno toModel(AlumnoCreateRequestDto dto);
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Alumno toModel(AlumnoUpdateRequestDto dto);
    AlumnoResponseDto toResponseDto(Alumno model);
}
