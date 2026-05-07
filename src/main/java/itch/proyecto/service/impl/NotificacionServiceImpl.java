package itch.proyecto.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth.client_sdk.client.UsuarioClient;
import com.auth.client_sdk.dto.UsuarioAuthDto;

import java.util.Collections;
import itch.proyecto.cliente.IncidenciaClient;
import itch.proyecto.dto.ActualizarEstadoNotificacionDto;
import itch.proyecto.dto.IncidenciaResponseDto;
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
    private final UsuarioClient usuarioClient;
    private final IncidenciaClient incidenciaClient;

    @Override
    public NotificacionResponseDto crearNotificacion(NotificacionRequestDto requestDto) {
        log.info("Creando notificación para incidencia ID: {}", requestDto.getIncidenciaId());

        // Obtener la incidencia con el ID
        IncidenciaResponseDto incidencia = incidenciaClient.obtenerIncidenciaPorId(requestDto.getIncidenciaId());

        if (incidencia == null) {
            throw new ResourceNotFoundException("Incidencia no encontrada con ID: " + requestDto.getIncidenciaId());
        }

        Long usuarioId = incidencia.getUsuarioId();

        // Obtener los datos del usuario
        UsuarioAuthDto usuario = usuarioClient.obtenerUsuario(usuarioId);

        if (usuario == null) {
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioId);
        }

        // Crear el mensaje de la notificación
        String mensajeFinal = "La incidencia \"" + incidencia.getTitulo() + "\" está en el estado: " + incidencia.getEstado();

        EstadoNotificacion estadoNotificacion = estadoNotificacionRepository.findById(requestDto.getEstadoId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado de notificación no encontrado con ID: " + requestDto.getEstadoId()));

        Notificacion notificacion = Notificacion.builder()
                .usuarioId(usuarioId)
                .incidenciaId(incidencia.getId())
                .mensaje(mensajeFinal)
                .estado(estadoNotificacion)
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
    @Transactional(readOnly = true)
    public List<NotificacionResponseDto> listarNotificacionesPorEstado(Long estadoId) {
        return notificacionRepository.findByEstadoId(estadoId).stream()
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

  
    @Override
    public List<UsuarioAuthDto> listarTodoslosUsuarios() {
        try {
            return usuarioClient.obtenerTodosLosUsuarios();
        } catch (Exception e) {
            log.error("Error al listar usuarios: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

}