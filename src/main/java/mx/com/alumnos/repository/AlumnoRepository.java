package mx.com.alumnos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.alumnos.entity.AlumnoEntity;

@Repository
public interface AlumnoRepository extends JpaRepository<AlumnoEntity, Long> {
	
	public boolean existsByCorreo(String correo);
	
}
