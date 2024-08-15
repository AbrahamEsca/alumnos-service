package mx.com.alumnos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import mx.com.alumnos.entity.AlumnoEntity;
import mx.com.alumnos.exception.RecordAlreadyExistsException;
import mx.com.alumnos.model.AlumnoRequestDTO;
import mx.com.alumnos.model.AlumnoResponseDTO;
import mx.com.alumnos.repository.AlumnoRepository;

@Service
public class AlumnoServiceImpl implements AlumnoService {
	
	private static final Logger LOGGER = LogManager.getLogger(AlumnoServiceImpl.class);
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	/**
	 * Descripcion: Metodo para crear un alumno
	 * 
	 * @param alumnoRequestDTO Body con los datos del nuevo alumno
	 * 
	 * @return AlumnoResponseDTO Respuesta del metodo
	 * 
	 **/
	@Override
	public AlumnoResponseDTO crearAlumno(AlumnoRequestDTO alumnoRequestDTO) {
		LOGGER.info("Inicio del metodo crearAlumno de la clase service");
		
		if (alumnoRepository.existsByCorreo(alumnoRequestDTO.getCorreo())) {
            throw new RecordAlreadyExistsException("Ya existe un alumno con el correo: " + alumnoRequestDTO.getCorreo());
        }
		
		AlumnoEntity nvoAlumno = convertirAEntity(alumnoRequestDTO);
		nvoAlumno = alumnoRepository.save(nvoAlumno);
		
		return convertirADTO(nvoAlumno);
	}
	
	/**
	 * Descripcion: Metodo para listar alumnos
	 * 
	 * @return List<AlumnoResponseDTO> Respuesta del metodo
	 * 
	 **/
	@Override
    public List<AlumnoResponseDTO> listarAlumnos() {
		LOGGER.info("Inicio del metodo listarAlumnos de la clase service");
		
        return alumnoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
	
	/**
	 * Descripcion: Metodo para obtener un alumno por id
	 * 
	 * @param idAlumno Dato del alumno a obtener
	 * 
	 * @return AlumnoResponseDTO Respuesta del metodo
	 * 
	 **/
	@Override
	public AlumnoResponseDTO obtenerAlumnoPorId(Long idAlumno) {
		LOGGER.info("Inicio del metodo obtenerAlumnoPorId de la clase service");

		return alumnoRepository.findById(idAlumno)
				.map(this::convertirADTO)
				.orElseThrow(() -> new EntityNotFoundException("Alumno no encontrado"));
	}
	
	/**
	 * Descripcion: Metodo para actualizar un alumno
	 * 
	 * @param idAlumno Dato del alumno que se requiere actualizar
	 * 
	 * @param alumnoRequestDTO Body con los datos nuevos del alumno
	 * 
	 * @return AlumnoResponseDTO Respuesta del metodo
	 * 
	 **/
	@Override
    public AlumnoResponseDTO actualizarAlumno(Long idAlumno, AlumnoRequestDTO alumnoRequestDTO) {
		LOGGER.info("Inicio del metodo actualizarAlumno de la clase service");
		
		AlumnoEntity alumno = alumnoRepository.findById(idAlumno)
                .orElseThrow(() -> new EntityNotFoundException("Alumno no encontrado"));
		
		alumno.setNombre(alumnoRequestDTO.getNombre());
		alumno.setApellidoPaterno(alumnoRequestDTO.getApellidoPaterno());
		alumno.setApellidoMaterno(alumnoRequestDTO.getApellidoMaterno());
		alumno.setEdad(alumnoRequestDTO.getEdad());
		alumno.setGrado(alumnoRequestDTO.getGrado());
		alumno.setCorreo(alumnoRequestDTO.getCorreo());
        
        alumno = alumnoRepository.save(alumno);
        
        return convertirADTO(alumno);
    }
	
	/**
	 * Descripcion: Metodo para eliminar un alumno
	 * 
	 * @param idAlumno Dato del alumno a eliminar
	 * 
	 * @return void
	 * 
	 **/
	@Override
    public void eliminarAlumno(Long idAlumno) {
		LOGGER.info("Inicio del metodo eliminarAlumno de la clase service");
		
        if (!alumnoRepository.existsById(idAlumno)) {
            throw new EntityNotFoundException("Alumno no encontrado");
        }
        
        alumnoRepository.deleteById(idAlumno);
    }
	
	/**
	 * Descripcion: Metodo para convertir una entidad a un DTO
	 * 
	 * @param alumno Datos del alumno
	 * 
	 * @return AlumnoResponseDTO Respuesta del metodo
	 * 
	 **/
	private AlumnoResponseDTO convertirADTO(AlumnoEntity alumno) {
		AlumnoResponseDTO alumnoResponseDTO = new AlumnoResponseDTO();
		BeanUtils.copyProperties(alumno, alumnoResponseDTO);

		String gradoTexto = switch (alumno.getGrado()) {
		case 1 -> "Primer Grado";
		case 2 -> "Segundo Grado";
		case 3 -> "Tercer Grado";
		default -> "Grado No Especificado";
		};
		alumnoResponseDTO.setGrado(gradoTexto);

		return alumnoResponseDTO;
	}
	
	/**
	 * Descripcion: Metodo para convertir un DTO a una Entidad
	 * 
	 * @param alumnoRequestDTO Datos del alumno
	 * 
	 * @return AlumnoEntity Respuesta del metodo
	 * 
	 **/
	private AlumnoEntity convertirAEntity(AlumnoRequestDTO alumnoRequestDTO) {
		AlumnoEntity alumno = new AlumnoEntity();
		BeanUtils.copyProperties(alumnoRequestDTO, alumno);
		
        return alumno;
    }

}
