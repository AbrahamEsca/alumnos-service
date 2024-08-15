package mx.com.alumnos.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlumnoRequestDTO {
	
	@NotBlank(message = "El nombre es obligatorio")
    @Size(max = 30, message = "El nombre no puede tener más de 30 caracteres")
    private String nombre;
	
	@NotBlank(message = "El apellido paterno es obligatorio")
    @Size(max = 30, message = "El apellido paterno no puede tener más de 30 caracteres")
    private String apellidoPaterno;
	
	@NotBlank(message = "El apellido materno es obligatorio")
    @Size(max = 30, message = "El apellido materno no puede tener más de 30 caracteres")
    private String apellidoMaterno;
	
	@NotNull(message = "La edad es obligatoria")
    @Min(value = 1, message = "La edad mínima debe ser 1")
    @Max(value = 120, message = "La edad máxima permitida es 120")
    private Integer edad;
	
	@NotNull(message = "El grado es obligatorio")
    @Min(value = 1, message = "El grado mínimo permitido es 1")
    @Max(value = 3, message = "El grado máximo permitido es 3")
    private Integer grado;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    private String correo;

}
