package com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.AulaDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.dto.PabellonDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Aula;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Pabellon;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-10T13:34:21-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class AulaMapperImpl implements AulaMapper {

    @Override
    public AulaDTO mapAula(Aula aula) {
        if ( aula == null ) {
            return null;
        }

        AulaDTO.AulaDTOBuilder aulaDTO = AulaDTO.builder();

        aulaDTO.id( aula.getId() );
        aulaDTO.pabellon( aula.getPabellon() );
        aulaDTO.nroAula( aula.getNroAula() );
        aulaDTO.medidas( aula.getMedidas() );
        aulaDTO.cantidadPupitres( aula.getCantidadPupitres() );
        aulaDTO.pizarron( aula.getPizarron() );

        return aulaDTO.build();
    }

    @Override
    public Aula mapAula(AulaDTO aulaDTO) {
        if ( aulaDTO == null ) {
            return null;
        }

        Aula aula = new Aula();

        aula.setId( aulaDTO.getId() );
        aula.setNroAula( aulaDTO.getNroAula() );
        aula.setMedidas( aulaDTO.getMedidas() );
        aula.setCantidadPupitres( aulaDTO.getCantidadPupitres() );
        aula.setPizarron( aulaDTO.getPizarron() );
        aula.setPabellon( aulaDTO.getPabellon() );

        return aula;
    }

    @Override
    public PabellonDTO mapPabellonToDTO(Pabellon pabellon) {
        if ( pabellon == null ) {
            return null;
        }

        PabellonDTO.PabellonDTOBuilder pabellonDTO = PabellonDTO.builder();

        pabellonDTO.id( pabellon.getId() );
        pabellonDTO.nombre( pabellon.getNombre() );
        pabellonDTO.direccion( pabellon.getDireccion() );

        return pabellonDTO.build();
    }
}
