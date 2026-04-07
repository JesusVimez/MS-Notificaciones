package itch.proyecto.service;

import java.util.List;

import itch.proyecto.dto.ActualizarEstadoNotificacionDto;
import itch.proyecto.dto.NotificacionRequestDto;
import itch.proyecto.dto.NotificacionResponseDto;

public interface NotificacionService {

    NotificacionResponseDto crearNotificacion(NotificacionRequestDto requestDto);

    NotificacionResponseDto obtenerNotificacionPorId(Long id);

    List<NotificacionResponseDto> listarTodasLasNotificaciones();

    List<NotificacionResponseDto> listarNotificacionesPorUsuarioId(Long usuarioId);

    NotificacionResponseDto actualizarEstado(Long id, ActualizarEstadoNotificacionDto dto);

    void eliminarNotificacion(Long id);
}