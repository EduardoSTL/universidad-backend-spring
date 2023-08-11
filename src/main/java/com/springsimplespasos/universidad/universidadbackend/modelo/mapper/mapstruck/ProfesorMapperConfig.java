package com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.ProfesorDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Profesor;
import org.mapstruct.*;

@MapperConfig
public interface ProfesorMapperConfig extends PersonaMapperConfig {
    @Mappings({
            @Mapping(target = "carreraDTO", source = "carrera"),
            @Mapping(target = "carreras", source = "carreras")
    })
    void mapProfesor(Profesor profesor, @MappingTarget ProfesorDTO profesorDTO);
}
