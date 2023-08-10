package com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.EmpleadoDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Empleado;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-10T13:34:22-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class EmpleadoMapperImpl implements EmpleadoMapper {

    @Override
    public EmpleadoDTO mapEmpleado(Empleado empleado) {
        if ( empleado == null ) {
            return null;
        }

        EmpleadoDTO empleadoDTO = new EmpleadoDTO();

        empleadoDTO.setId( empleado.getId() );
        empleadoDTO.setNombre( empleado.getNombre() );
        empleadoDTO.setApellido( empleado.getApellido() );
        empleadoDTO.setDni( empleado.getDni() );
        empleadoDTO.setDireccion( empleado.getDireccion() );
        empleadoDTO.setSueldo( empleado.getSueldo() );
        empleadoDTO.setTipoEmpleado( empleado.getTipoEmpleado() );
        empleadoDTO.setPabellon( empleado.getPabellon() );

        return empleadoDTO;
    }

    @Override
    public Empleado mapEmpleadoDTO(EmpleadoDTO empleadoDTO) {
        if ( empleadoDTO == null ) {
            return null;
        }

        Empleado empleado = new Empleado();

        empleado.setId( empleadoDTO.getId() );
        empleado.setNombre( empleadoDTO.getNombre() );
        empleado.setApellido( empleadoDTO.getApellido() );
        empleado.setDni( empleadoDTO.getDni() );
        empleado.setDireccion( empleadoDTO.getDireccion() );
        empleado.setSueldo( empleadoDTO.getSueldo() );
        empleado.setTipoEmpleado( empleadoDTO.getTipoEmpleado() );
        empleado.setPabellon( empleadoDTO.getPabellon() );

        return empleado;
    }
}
