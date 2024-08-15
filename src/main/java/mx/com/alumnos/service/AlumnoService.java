package mx.com.alumnos.service;

import java.util.List;

import mx.com.alumnos.model.AlumnoRequestDTO;
import mx.com.alumnos.model.AlumnoResponseDTO;

public interface AlumnoService {
	
	public AlumnoResponseDTO crearAlumno(AlumnoRequestDTO request);
	
	public List<AlumnoResponseDTO> listarAlumnos();
	
	public AlumnoResponseDTO obtenerAlumnoPorId(Long idAlumno);
	
	public AlumnoResponseDTO actualizarAlumno(Long idAlumno, AlumnoRequestDTO alumnoRequestDTO);
	
	public void eliminarAlumno(Long idAlumno);

}
