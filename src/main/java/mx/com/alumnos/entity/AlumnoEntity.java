package mx.com.alumnos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "alumnos")
public class AlumnoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_alumno_pk")
	private Long idAlumno;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "apellido_paterno")
	private String apellidoPaterno;
	
	@Column(name = "apellido_materno")
	private String apellidoMaterno;
	
	@Column(name = "edad")
	private Integer edad;
	
	@Column(name = "grado")
	private Integer grado;
	
	@Column(name = "correo")
	private String correo;

}
