package itch.proyecto.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import itch.proyecto.dto.ActualizarEstadoNotificacionDto;
import itch.proyecto.dto.NotificacionRequestDto;
import itch.proyecto.dto.NotificacionResponseDto;
import itch.proyecto.entity.EstadoNotificacion;
import itch.proyecto.entity.Notificacion;
import itch.proyecto.exception.ResourceNotFoundException;
import itch.proyecto.mapper.NotificacionMapper;
import itch.proyecto.repository.EstadoNotificacionRepository;
import itch.proyecto.repository.NotificacionRepository;
import itch.proyecto.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final EstadoNotificacionRepository estadoNotificacionRepository;
    private final NotificacionMapper notificacionMapper;

    @Override
    public NotificacionResponseDto crearNotificacion(NotificacionRequestDto requestDto) {
        log.info("Creando notificación para usuario ID: {}", requestDto.getUsuarioId());

        EstadoNotificacion estado = estadoNotificacionRepository.findById(requestDto.getEstadoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Estado de notificación no encontrado con ID: " + requestDto.getEstadoId()));

        Notificacion notificacion = Notificacion.builder()
                .usuarioId(requestDto.getUsuarioId())
                .incidenciaId(requestDto.getIncidenciaId())
                .mensaje(requestDto.getMensaje())
                .estado(estado)
                .build();

        Notificacion guardada = notificacionRepository.save(notificacion);

        log.info("Notificación creada con ID: {}", guardada.getId());
        return notificacionMapper.toResponseDto(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public NotificacionResponseDto obtenerNotificacionPorId(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Notificación no encontrada con ID: " + id));

        return notificacionMapper.toResponseDto(notificacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificacionResponseDto> listarTodasLasNotificaciones() {
        return notificacionRepository.findAll().stream()
                .map(notificacionMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificacionResponseDto> listarNotificacionesPorUsuarioId(Long usuarioId) {
        return notificacionRepository.findByUsuarioIdOrderByFechaDesc(usuarioId).stream()
                .map(notificacionMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public NotificacionResponseDto actualizarEstado(Long id, ActualizarEstadoNotificacionDto dto) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Notificación no encontrada con ID: " + id));

        EstadoNotificacion estado = estadoNotificacionRepository.findById(dto.getEstadoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Estado de notificación no encontrado con ID: " + dto.getEstadoId()));

        notificacion.setEstado(estado);

        Notificacion actualizada = notificacionRepository.save(notificacion);
        return notificacionMapper.toResponseDto(actualizada);
    }

    @Override
    public void eliminarNotificacion(Long id) {
        if (!notificacionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notificación no encontrada con ID: " + id);
        }

        notificacionRepository.deleteById(id);
    }
}