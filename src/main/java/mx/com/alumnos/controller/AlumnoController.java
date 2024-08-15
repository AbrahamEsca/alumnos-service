package mx.com.alumnos.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import mx.com.alumnos.model.AlumnoRequestDTO;
import mx.com.alumnos.model.AlumnoResponseDTO;
import mx.com.alumnos.service.AlumnoService;

@RestController
@RequestMapping("/api/alumnos")
@Tag(name = "Alumnos", description = "API para gestionar alumnos")
@Validated
public class AlumnoController {
	
	private static final Logger LOGGER = LogManager.getLogger(AlumnoController.class);
	
	@Autowired
	private AlumnoService alumnoService;
	
	/**
	 * Descripcion: Servicio para crear un alumno
	 * 
	 * @param alumnoRequestDTO Body con los datos del nuevo alumno
	 * 
	 * @return ResponseEntity Respuesta del servicio
	 * 
	 **/
	@PostMapping
	@Operation(summary = "Crear un nuevo alumno")
    public ResponseEntity<AlumnoResponseDTO> crearAlumno(@Valid @RequestBody AlumnoRequestDTO alumnoRequestDTO) {
		LOGGER.info("Inicio del metodo crearAlumno de la clase controller");
		
        AlumnoResponseDTO nuevoAlumno = alumnoService.crearAlumno(alumnoRequestDTO);
        
        return new ResponseEntity<>(nuevoAlumno, HttpStatus.CREATED);
    }
	
	/**
	 * Descripcion: Servicio para listar alumnos
	 * 
	 * @return ResponseEntity Respuesta del servicio
	 * 
	 **/
	@GetMapping
	@Operation(summary = "Listar alumnos")
	public ResponseEntity<List<AlumnoResponseDTO>> listarAlumnos() {
		LOGGER.info("Inicio del metodo listarAlumnos de la clase controller");
		
        return ResponseEntity.ok(alumnoService.listarAlumnos());
    }
	
	/**
	 * Descripcion: Servicio para obtener un alumno por id
	 * 
	 * @param idAlumno Dato del alumno a obtener
	 * 
	 * @return ResponseEntity Respuesta del servicio
	 * 
	 **/
	@GetMapping("/{id}")
	@Operation(summary = "Obtener un alumno por Id")
    public ResponseEntity<AlumnoResponseDTO> obtenerAlumnoPorId(@PathVariable("id") @Min(1) Long idAlumno) {
		LOGGER.info("Inicio del metodo obtenerAlumnoPorId de la clase controller");
		
        return ResponseEntity.ok(alumnoService.obtenerAlumnoPorId(idAlumno));
    }
	
	/**
	 * Descripcion: Servicio para actualizar un alumno
	 * 
	 * @param idAlumno Dato del alumno que se requiere actualizar
	 * 
	 * @param alumnoRequestDTO Body con los datos nuevos del alumno
	 * 
	 * @return ResponseEntity Respuesta del servicio
	 * 
	 **/
	@PutMapping("/{id}")
	@Operation(summary = "Actualizar un alumno por Id")
    public ResponseEntity<AlumnoResponseDTO> actualizarAlumno(@PathVariable("id") @Min(1) Long idAlumno,
                                                              @Valid @RequestBody AlumnoRequestDTO alumnoRequestDTO) {
		LOGGER.info("Inicio del metodo actualizarAlumno de la clase controller");
		
        AlumnoResponseDTO alumnoActualizado = alumnoService.actualizarAlumno(idAlumno, alumnoRequestDTO);
        
        return ResponseEntity.ok(alumnoActualizado);
    }
	
	/**
	 * Descripcion: Servicio para eliminar un alumno
	 * 
	 * @param idAlumno Dato del alumno a eliminar
	 * 
	 * @return ResponseEntity Respuesta del servicio
	 * 
	 **/
	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar un alumno")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable("id") @Min(1) Long idAlumno) {
		LOGGER.info("Inicio del metodo eliminarAlumno de la clase controller");
		
        alumnoService.eliminarAlumno(idAlumno);
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
