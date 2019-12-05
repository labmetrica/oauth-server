package com.metrica.formacion.oauthserver.dao;

import com.metrica.formacion.oauthserver.entity.usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface usuariosRepository extends JpaRepository<usuarios, Integer> {

	// Borrar

	void deleteByEmailContainingIgnoreCase(String email);

	// Busqueda por nombre y apellido

    usuarios findByUsername(String username);

    List<usuarios> findByNombreContainingIgnoreCase(String nombre);

	List<usuarios> findByApellidoContainingIgnoreCase(String apellido);

	List<usuarios> findByNombreOrApellidoContainingIgnoreCase(String nombre, String apellido);

	List<usuarios> findByGrupo(Integer idgrupo);

	usuarios findByEmailContainingIgnoreCase(String email);

	// CreatedAT

	@Query(value = "SELECT * FROM usuarios WHERE DATE(usuarios.createdAT) = ?1", nativeQuery = true)
	List<usuarios> findByCreatedAT(String date);

	List<usuarios> findByCreatedATBetween(LocalDateTime fecha1, LocalDateTime fecha2);

	List<usuarios> findByCreatedATBefore(LocalDateTime localDateTime);

	// Ultima modificacion

	@Query(value = "SELECT * FROM usuarios WHERE DATE(usuarios.ultimaModificacion) = ?1", nativeQuery = true)
	List<usuarios> findByUltimaModificacion(String fecha);

	List<usuarios> findByUltimaModificacionBefore(LocalDateTime localDateTime);

	List<usuarios> findByUltimaModificacionBetween(LocalDateTime fecha1, LocalDateTime fecha2);

	boolean existsByEmailContainingIgnoreCase(String email);
}
