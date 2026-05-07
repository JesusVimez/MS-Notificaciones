package itch.proyecto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import itch.proyecto.dto.ActualizarEstadoNotificacionDto;
import itch.proyecto.dto.NotificacionRequestDto;
import itch.proyecto.dto.NotificacionResponseDto;
import com.auth.client_sdk.dto.UsuarioAuthDto;

import itch.proyecto.service.NotificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
@Slf4j
public class NotificacionController {

    private final NotificacionService notificacionService;

   
    @PostMapping
    public ResponseEntity<NotificacionResponseDto> crearNotificacion(
            @Valid @RequestBody NotificacionRequestDto requestDto) {
        log.info("POST - Crear notificación para incidencia ID: {}", requestDto.getIncidenciaId());
        NotificacionResponseDto response = notificacionService.crearNotificacion(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponseDto> obtenerNotificacionPorId(@PathVariable Long id) {
        log.info("GET - Obtener notificación con ID: {}", id);
        NotificacionResponseDto response = notificacionService.obtenerNotificacionPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<NotificacionResponseDto>> listarTodasLasNotificaciones() {
        log.info("GET - Listar todas las notificaciones");
        List<NotificacionResponseDto> response = notificacionService.listarTodasLasNotificaciones();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificacionResponseDto>> listarNotificacionesPorUsuarioId(
            @PathVariable Long usuarioId) {
        log.info("GET - Listar notificaciones por usuario ID: {}", usuarioId);
        List<NotificacionResponseDto> response = notificacionService.listarNotificacionesPorUsuarioId(usuarioId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estado/{estadoId}")
    public ResponseEntity<List<NotificacionResponseDto>> listarNotificacionesPorEstado(
            @PathVariable Long estadoId) {
        log.info("GET - Listar notificaciones por estado ID: {}", estadoId);
        List<NotificacionResponseDto> response = notificacionService.listarNotificacionesPorEstado(estadoId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<NotificacionResponseDto> actualizarEstado(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarEstadoNotificacionDto dto) {
        log.info("PUT - Actualizar estado de notificación con ID: {}", id);
        NotificacionResponseDto response = notificacionService.actualizarEstado(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable Long id) {
        log.info("DELETE - Eliminar notificación con ID: {}", id);
        notificacionService.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }

    // Nueva ruta para listar todos los usuarios
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioAuthDto>> listarTodoslosUsuarios() {
        log.info("GET - Listar todos los usuarios");
        List<UsuarioAuthDto> response = notificacionService.listarTodoslosUsuarios();
        return ResponseEntity.ok(response);
    }
}