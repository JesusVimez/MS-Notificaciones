package itch.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import itch.proyecto.entity.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByUsuarioId(Long usuarioId);

	List<Notificacion> findByUsuarioIdOrderByFechaDesc(Long usuarioId);
	List<Notificacion> findByEstadoId(Long estadoId);

}