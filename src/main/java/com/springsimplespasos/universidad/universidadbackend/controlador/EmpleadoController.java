
package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exception.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Empleado;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.enumeradores.TipoEmpleado;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.EmpleadoDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Deprecated
@RestController
@RequestMapping("/empleados")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "false")
public class EmpleadoController extends PersonaController{
    public EmpleadoController(@Qualifier("empleadoDAOImpl") PersonaDAO service) {
        super(service);
        nombreEntidad = "Empleado";
    }

    @GetMapping("/tipo-empleado")
    public Iterable<Persona> buscarEmpleadosPorTipoEmpleado(@RequestBody TipoEmpleado tipoEmpleado){
        return ((EmpleadoDAO)service).buscarEmpleadosPorTipoEmpleado(tipoEmpleado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEmpleado(@PathVariable Integer id, @RequestBody Empleado empleado){
        Map<String, Object> mensaje = new HashMap<>();
        Empleado empleadoUpdate;
        Optional<Persona> oEmpleado = service.findById(id);
        if(!oEmpleado.isPresent()) {
            //throw new BadRequestException(String.format("Empleado con id %d no existe", id));
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("mensaje", String.format("Empleado con id %d no existe", id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        empleadoUpdate = (Empleado) oEmpleado.get();
        empleadoUpdate.setNombre(empleado.getNombre());
        empleadoUpdate.setApellido(empleado.getApellido());
        empleadoUpdate.setDireccion(empleado.getDireccion());
        empleadoUpdate.setTipoEmpleado(empleado.getTipoEmpleado());
        empleadoUpdate.setSueldo(empleado.getSueldo());
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("datos", service.save(empleadoUpdate));
        return ResponseEntity.ok(mensaje);
    }
}