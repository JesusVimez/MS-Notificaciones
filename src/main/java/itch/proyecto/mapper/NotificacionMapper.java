package itch.proyecto.mapper;

import org.springframework.stereotype.Component;

import itch.proyecto.dto.NotificacionResponseDto;
import itch.proyecto.entity.Notificacion;

@Component
public class NotificacionMapper {

    public NotificacionResponseDto toResponseDto(Notificacion notificacion) {
        return NotificacionResponseDto.builder()
                .id(notificacion.getId())
                .usuarioId(notificacion.getUsuarioId())
                .incidenciaId(notificacion.getIncidenciaId())
                .mensaje(notificacion.getMensaje())
                .fecha(notificacion.getFecha().toString())
                .estadoId(notificacion.getEstado().getId())
                .estadoNombre(notificacion.getEstado().getNombre())
                .build();
    }
}