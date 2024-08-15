package mx.com.alumnos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlumnoResponseDTO {
	    
	private Long idAlumno;
	
    private String nombre;
    
    private String apellidoPaterno;
    
    private String apellidoMaterno;
    
    private Integer edad;
    
    private String grado;
    
    private String correo;

}
