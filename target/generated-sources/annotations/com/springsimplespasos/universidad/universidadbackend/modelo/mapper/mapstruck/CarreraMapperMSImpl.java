package com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.CarreraDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-10T19:04:49-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class CarreraMapperMSImpl implements CarreraMapperMS {

    @Override
    public CarreraDTO mapCarrera(Carrera carrera) {
        if ( carrera == null ) {
            return null;
        }

        CarreraDTO.CarreraDTOBuilder carreraDTO = CarreraDTO.builder();

        carreraDTO.codigo( carrera.getId() );
        carreraDTO.nombre( carrera.getNombre() );
        carreraDTO.cantidad_materias( carrera.getCantidadMaterias() );
        carreraDTO.cantidad_anios( carrera.getCantidadAnios() );

        return carreraDTO.build();
    }

    @Override
    public CarreraDTO mapCarrera(CarreraDTO carreraDTO) {
        if ( carreraDTO == null ) {
            return null;
        }

        CarreraDTO.CarreraDTOBuilder carreraDTO1 = CarreraDTO.builder();

        carreraDTO1.codigo( carreraDTO.getId() );
        carreraDTO1.nombre( carreraDTO.getNombre() );
        carreraDTO1.cantidad_materias( carreraDTO.getCantidadMaterias() );
        carreraDTO1.cantidad_anios( carreraDTO.getCantidadAnios() );

        return carreraDTO1.build();
    }
}
