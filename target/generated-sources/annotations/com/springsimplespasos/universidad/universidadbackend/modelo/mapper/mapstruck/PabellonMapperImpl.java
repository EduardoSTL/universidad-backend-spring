package com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.AulaDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.dto.PabellonDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Aula;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Pabellon;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-10T13:34:22-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class PabellonMapperImpl implements PabellonMapper {

    @Override
    public PabellonDTO mapPabellonToDTO(Pabellon pabellon) {
        if ( pabellon == null ) {
            return null;
        }

        PabellonDTO.PabellonDTOBuilder pabellonDTO = PabellonDTO.builder();

        pabellonDTO.id( pabellon.getId() );
        pabellonDTO.nombre( pabellon.getNombre() );
        pabellonDTO.mts2( pabellon.getMts2() );
        pabellonDTO.direccion( pabellon.getDireccion() );

        return pabellonDTO.build();
    }

    @Override
    public Pabellon mapPabellonToEntity(PabellonDTO pabellonDTO) {
        if ( pabellonDTO == null ) {
            return null;
        }

        Pabellon pabellon = new Pabellon();

        pabellon.setId( pabellonDTO.getId() );
        pabellon.setMts2( pabellonDTO.getMts2() );
        pabellon.setNombre( pabellonDTO.getNombre() );
        pabellon.setDireccion( pabellonDTO.getDireccion() );

        return pabellon;
    }

    @Override
    public AulaDTO mapAula(Aula aula) {
        if ( aula == null ) {
            return null;
        }

        AulaDTO.AulaDTOBuilder aulaDTO = AulaDTO.builder();

        aulaDTO.id( aula.getId() );
        aulaDTO.nroAula( aula.getNroAula() );

        return aulaDTO.build();
    }
}
