package com.springsimplespasos.universidad.universidadbackend.controlador.dto;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.AlumnoDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.dto.PersonaDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck.AlumnoMapper;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck.CarreraMapperMS;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.AlumnoDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.CarreraDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alumnos")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "true")
@Tag(name = "Alumnos", description = "Registro de alumnos")
public class AlumnoDtoController extends PersonaDtoController{

    private final CarreraDAO carreraDAO;
    private final CarreraMapperMS carreraMapperMS;

    public AlumnoDtoController(@Qualifier("alumnoDAOImpl") PersonaDAO service, AlumnoMapper alumnoMapper, CarreraDAO carreraDAO, CarreraMapperMS carreraMapperMS){
        super(service, "Alumno", alumnoMapper);
        this.carreraDAO = carreraDAO;
        this.carreraMapperMS = carreraMapperMS;
    }

    @Operation(summary = "Obtener todos los registros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "todos los registros de alumnos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
    })
    @GetMapping
    public ResponseEntity<?> findAllAlumnos(){
        Map<String, Object> mensaje = new HashMap<>();
        List<PersonaDTO> dtos = super.findAll();
        mensaje.put("succes", Boolean.TRUE);
        mensaje.put("data", dtos);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Buscar alumno por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "registro de alumno",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "404 Not Found", description = "registro de alumno no existe",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "500 Internal Server Error", description = "server error",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findAlumnoById(@Parameter(description = "digita el id del alumno")@PathVariable Integer id){
        Map<String, Object> mensaje = new HashMap<>();
        PersonaDTO dto = super.findPersonaById(id);
        if (dto == null){
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje", String.format("No existe %s con ID %d", nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("data", dto);
        return ResponseEntity.ok(mensaje);
    }

    @Operation(summary = "Crear un registro de alumno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "registro creado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "400 Bad Request", description = "error al crear un nuevo alumno",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "500 Internal Server Error", description = "server error",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
    })
    @PostMapping
    public ResponseEntity<?> createAlumno(@Parameter(description = "crear un nuevo alumno")@Valid @RequestBody PersonaDTO personaDTO, BindingResult result){
        Map<String, Object> mensaje = new HashMap<>();
        if (result.hasErrors()){
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("validaciones", super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(mensaje);
        }
        Persona persona = alumnoMapper.mapAlumno((AlumnoDTO) personaDTO);
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("data", super.createEntidadPersona(persona));
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }

    @Operation(summary = "Actualizar un registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Se modifico el registro ",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "400 Bad Request", description = "No se pudo crear el registro: Datos no validos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "404 Not Found", description = "No existe registro con id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarAlumno(@PathVariable Integer id, @Valid @RequestBody AlumnoDTO alumnoDTO,BindingResult result){
        Map<String,Object> mensaje = new HashMap<>();
        PersonaDTO personaDTO= super.findPersonaById(id);
        AlumnoDTO dto;
        Alumno alumnoUpdate;

        if(personaDTO == null) {
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("mensaje",String.format("%s con id %d no existe",nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        if (result.hasErrors()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("validaciones",super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(mensaje);
        }
        dto = ((AlumnoDTO)personaDTO);
        dto.setNombre(alumnoDTO.getNombre());
        dto.setApellido(alumnoDTO.getApellido());
        dto.setDireccion(alumnoDTO.getDireccion());
        dto.setDni(alumnoDTO.getDni());
        alumnoUpdate = alumnoMapper.mapAlumno(dto);
        mensaje.put("datos",super.createEntidadPersona(alumnoUpdate));
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Eliminar un registro por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Registro eliminado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "404 Not Found", description = "No existe registro con id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlumnoId(@PathVariable Integer id){
        Map<String,Object> mensaje = new HashMap<>();
        PersonaDTO personaDTO = super.findPersonaById(id);
        if(personaDTO==null) {
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje",  String.format("No existe %s con Id %d", nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        super.deletePersonaById(id);
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("message", String.format("Se eliminó %s con Id %d", nombre_entidad, id));
        return ResponseEntity.status(HttpStatus.OK).body(mensaje);
    }

    @Operation(summary = "Buscar registro por nombre y apellido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Se encontro el registro ",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "404 Not Found", description = "No existe registro con id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
    })
    @GetMapping("/nombre-apellido/{nombre}/{apellido}")
    public ResponseEntity<?> findAlumnoNombreApellido(@PathVariable String nombre, @PathVariable String apellido){
        Map<String,Object> mensaje = new HashMap<>();
        PersonaDTO personaDTO = super.findPersonaByNombreYApellido(nombre,apellido);
        if (personaDTO==null){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("mensaje",String.format("No se encontro persona con nombre +%s y appelido %s",nombre,apellido));
            return ResponseEntity.badRequest().body(mensaje);
        }
        mensaje.put("datos",personaDTO);
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Buscar registro por DNI")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Se encontro el registro ",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "404 Not Found", description = "No existe registro con DNI",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
    })
    @GetMapping("/alumno-dni")
    public ResponseEntity<Map<String, Object>> findAlumnoDni(@RequestParam String dni){
        Map<String,Object> mensaje = new HashMap<>();
        PersonaDTO dto = super.findPersonaByDni(dni);
        if (dto == null){
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje", String.format("No se encontro %s con DNI: %s",nombre_entidad,dni));
            return ResponseEntity.badRequest().body(mensaje);
        }
        mensaje.put("datos",dto);
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Asignar carrera a alumno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Se asigno carrera ",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "404 Not Found", description = "No existe registro con id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
    })
    @PutMapping("/{idAlumno}/carrera/{idCarrera}")
    public ResponseEntity<?> assignCarreraAlumno(@PathVariable Integer idAlumno, @PathVariable Integer idCarrera){
        Map<String,Object> mensaje= new HashMap<>();
        PersonaDTO oAlumno = super.findPersonaById(idAlumno);
        Alumno alumno;
        Carrera carrera;
        Optional<Carrera> oCarrera;
        if(oAlumno==null) {
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("mensaje",String.format("Alumno con id %d no existe", idAlumno));
            return ResponseEntity.badRequest().body(mensaje);
        }
        oCarrera = carreraDAO.findById(idCarrera);
        if(oCarrera.isEmpty()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("mensaje",String.format("Carrera con id %d no existe",idCarrera ));
            return ResponseEntity.badRequest().body(mensaje);
        }
        alumno = alumnoMapper.mapAlumno((AlumnoDTO) oAlumno);
        carrera = oCarrera.get();
        alumno.setCarrera(carrera);
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",service.save(alumno));
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Buscar todos los alumnos de una carrera")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Se encontro los registros ",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "404 Not Found", description = "No existe registro",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
    })
    @GetMapping("/alumnos-carrera/{carrera}")
    public ResponseEntity<?> findAllAlumnosByCarrera(@PathVariable String carrera){
        Map<String,Object> mensaje= new HashMap<>();
        List<Persona> alumnos = ((List<Persona>)((AlumnoDAO)service).buscarAlumnosPorNombreCarrera(carrera));
        if (alumnos.isEmpty()){
            mensaje.put("success", Boolean.TRUE);
            mensaje.put("mensaje", String.format("No existen alumnos en carrera: %s ",carrera));
            return ResponseEntity.badRequest().body(mensaje);
        }
        List<AlumnoDTO> dtos =alumnos.stream()
                .map(persona -> alumnoMapper.mapAlumno((Alumno) persona))
                .collect(Collectors.toList());
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",dtos);
        return  ResponseEntity.ok().body(mensaje);
    }
}