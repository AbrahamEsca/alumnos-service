package mx.com.stefanini.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import jakarta.persistence.EntityNotFoundException;
import mx.com.alumnos.entity.AlumnoEntity;
import mx.com.alumnos.exception.RecordAlreadyExistsException;
import mx.com.alumnos.model.AlumnoRequestDTO;
import mx.com.alumnos.model.AlumnoResponseDTO;
import mx.com.alumnos.repository.AlumnoRepository;
import mx.com.alumnos.service.AlumnoServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class AlumnoServiceTest {

	@Mock
    private AlumnoRepository alumnoRepository;

    @InjectMocks
    private AlumnoServiceImpl alumnoServiceImpl;
    
	@Test
	public void testCrearAlumno() {
		AlumnoRequestDTO requestDTO = new AlumnoRequestDTO("Abraham", "Escalante", "Flores", 12, 1, "abra_esc@outlook.com");
		AlumnoEntity alumnoEntity = new AlumnoEntity(1L, "Abraham", "Escalante", "Flores", 12, 1, "abra_esc@outlook.com");
		
		when(alumnoRepository.existsByCorreo(anyString())).thenReturn(false);
		
		when(alumnoRepository.save(any(AlumnoEntity.class))).thenReturn(alumnoEntity);
		
		AlumnoResponseDTO alumnoResponseDTO = alumnoServiceImpl.crearAlumno(requestDTO);
		
		assertEquals(requestDTO.getNombre(), alumnoResponseDTO.getNombre());
	}
	
	@Test
	public void testCrearAlumnoRecordAlreadyExistsException() {
		AlumnoRequestDTO requestDTO = new AlumnoRequestDTO("Abraham", "Escalante", "Flores", 12, 1, "abra_esc@outlook.com");
		
		when(alumnoRepository.existsByCorreo(anyString())).thenReturn(true);
		
		Assertions.assertThrows(RecordAlreadyExistsException.class, () -> alumnoServiceImpl.crearAlumno(requestDTO));
	}
	
	@Test
	public void testListarAlumnos() {
		AlumnoEntity alumnoUnoEntity = new AlumnoEntity(1L, "Abraham", "Escalante", "Flores", 12, 1, "abra_esc@outlook.com");
		AlumnoEntity alumnoDosEntity = new AlumnoEntity(1L, "Abraham", "Escalante", "Flores", 12, 1, "abra_esc@outlook.com");
		
		List<AlumnoEntity> alumnoEntityList = new ArrayList<>();
		alumnoEntityList.add(alumnoUnoEntity);
		alumnoEntityList.add(alumnoDosEntity);
		
		when(alumnoRepository.findAll()).thenReturn(alumnoEntityList);
		
		List<AlumnoResponseDTO> alumnoResponseDTOList = alumnoServiceImpl.listarAlumnos();
		
		assertEquals(2, alumnoResponseDTOList.size());
	}
	
	@Test
	public void testObtenerAlumnoPorId() {
		AlumnoEntity alumnoEntity = new AlumnoEntity(1L, "Abraham", "Escalante", "Flores", 12, 1, "abra_esc@outlook.com");
		Optional<AlumnoEntity> alumnoOpt = Optional.of(alumnoEntity);
		
		when(alumnoRepository.findById(anyLong())).thenReturn(alumnoOpt);
		
		AlumnoResponseDTO alumnoResponseDTO = alumnoServiceImpl.obtenerAlumnoPorId(1L);
		
		assertEquals(alumnoEntity.getNombre(), alumnoResponseDTO.getNombre());
	}
	
	@Test
	public void testObtenerAlumnoPorIdEntityNotFoundException() {
		when(alumnoRepository.findById(anyLong())).thenThrow(new EntityNotFoundException("Alumno no encontrado"));
		
		Assertions.assertThrows(EntityNotFoundException.class, () -> alumnoServiceImpl.obtenerAlumnoPorId(10L));
	}
	
	@Test
	public void testActualizarAlumno() {
		AlumnoRequestDTO requestDTO = new AlumnoRequestDTO("Abraham", "Escalante", "Flores", 13, 2, "abra_esc@outlook.com");
		AlumnoEntity alumnoEntity = new AlumnoEntity(1L, "Abraham", "Escalante", "Flores", 12, 1, "abra_esc@outlook.com");
		Optional<AlumnoEntity> alumnoOpt = Optional.of(alumnoEntity);
		
		when(alumnoRepository.findById(anyLong())).thenReturn(alumnoOpt);
		
		when(alumnoRepository.save(any(AlumnoEntity.class))).thenReturn(alumnoEntity);
		
		AlumnoResponseDTO alumnoResponseDTO = alumnoServiceImpl.actualizarAlumno(1L, requestDTO);
		
		assertEquals(requestDTO.getEdad(), alumnoResponseDTO.getEdad());
	}
	
	@Test
	public void testActualizarAlumnoEntityNotFoundException() {
		AlumnoRequestDTO requestDTO = new AlumnoRequestDTO("Abraham", "Escalante", "Flores", 12, 1, "abra_esc@outlook.com");
		
		when(alumnoRepository.findById(anyLong())).thenThrow(new EntityNotFoundException("Alumno no encontrado"));
		
		Assertions.assertThrows(EntityNotFoundException.class, () -> alumnoServiceImpl.actualizarAlumno(10L, requestDTO));
	}
	
	@Test
	public void testEliminarAlumno() {
		when(alumnoRepository.existsById(anyLong())).thenReturn(true);
		
		doNothing().when(alumnoRepository).deleteById(anyLong());
		
		alumnoServiceImpl.eliminarAlumno(1L);
		
		verify(alumnoRepository, times(1)).deleteById(1L);
	}
	
	@Test
	public void testEliminarAlumnoEntityNotFoundException() {
		when(alumnoRepository.existsById(anyLong())).thenReturn(false);
		
		Assertions.assertThrows(EntityNotFoundException.class, () -> alumnoServiceImpl.eliminarAlumno(10L));
	}

}
