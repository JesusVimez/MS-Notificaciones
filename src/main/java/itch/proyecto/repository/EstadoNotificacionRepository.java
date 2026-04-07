package itch.proyecto.repository;

import itch.proyecto.entity.EstadoNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EstadoNotificacionRepository extends JpaRepository<EstadoNotificacion, Long> {

    Optional<EstadoNotificacion> findByNombre(String nombre);

}
