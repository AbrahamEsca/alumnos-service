package mx.com.alumnos.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import mx.com.alumnos.model.AlumnoRequestDTO;
import mx.com.alumnos.model.AlumnoResponseDTO;
import mx.com.alumnos.service.AlumnoService;

@RunWith(MockitoJUnitRunner.class)
public class AlumnoControllerTest {

	@Mock
    private AlumnoService alumnoService;

    @InjectMocks
    private AlumnoController alumnoController;
    
	@Test	
	public void testCrearAlumno() {
		AlumnoRequestDTO requestDTO = new AlumnoRequestDTO("Abraham", "Escalante", "Flores", 12, 1, "abra_esc@outlook.com");
        AlumnoResponseDTO responseDTO = new AlumnoResponseDTO(1L, "Abraham", "Escalante", "Flores", 12, "Primer grado", "abra_esc@outlook.com");
        
		when(alumnoService.crearAlumno(any(AlumnoRequestDTO.class))).thenReturn(responseDTO);
		
		ResponseEntity<AlumnoResponseDTO> resp = alumnoController.crearAlumno(requestDTO);
		assertEquals(HttpStatus.CREATED, resp.getStatusCode());
	}

	@Test	
	public void testListarAlumnos() {
		AlumnoResponseDTO responseUnoDTO = new AlumnoResponseDTO(1L, "Abraham", "Escalante", "Flores", 12, "Primer grado", "abra_esc@outlook.com");
		AlumnoResponseDTO responseDosDTO = new AlumnoResponseDTO(2L, "Diana", "Garcia", "Lopez", 15, "Tercer grado", "diana_gar@outlook.com");
		List<AlumnoResponseDTO> alumnoResponseDTOList = new ArrayList<>();
		alumnoResponseDTOList.add(responseUnoDTO);
		alumnoResponseDTOList.add(responseDosDTO);
		
		when(alumnoService.listarAlumnos()).thenReturn(alumnoResponseDTOList);
		
		ResponseEntity<List<AlumnoResponseDTO>> resp = alumnoController.listarAlumnos();
		assertEquals(HttpStatus.OK, resp.getStatusCode());
	}
	
	@Test	
	public void testObtenerAlumnoPorId() {
		AlumnoResponseDTO responseDTO = new AlumnoResponseDTO(1L, "Abraham", "Escalante", "Flores", 12, "Primer grado", "abra_esc@outlook.com");
		
		when(alumnoService.obtenerAlumnoPorId(anyLong())).thenReturn(responseDTO);
		
		ResponseEntity<AlumnoResponseDTO> resp = alumnoController.obtenerAlumnoPorId(1L);
		assertEquals(HttpStatus.OK, resp.getStatusCode());
	}
	
	@Test	
	public void testActualizarAlumno() {
		AlumnoRequestDTO requestDTO = new AlumnoRequestDTO("Josue", "Samperio", "Rojas", 13, 1, "josue@outlook.com");
        AlumnoResponseDTO responseDTO = new AlumnoResponseDTO(1L, "Josue", "Samperio", "Rojas", 13, "Primer grado", "josue@outlook.com");
        
		when(alumnoService.actualizarAlumno(anyLong(), any(AlumnoRequestDTO.class))).thenReturn(responseDTO);
		
		ResponseEntity<AlumnoResponseDTO> resp = alumnoController.actualizarAlumno(1L, requestDTO);
		assertEquals(HttpStatus.OK, resp.getStatusCode());
	}
	
	@Test	
	public void testEliminarAlumno() {
		doNothing().when(alumnoService).eliminarAlumno(anyLong());
		
		alumnoController.eliminarAlumno(1L);
		ResponseEntity<Void> resp = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());
	}

}
