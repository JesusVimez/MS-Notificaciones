package itch.proyecto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import itch.proyecto.dto.ActualizarEstadoNotificacionDto;
import itch.proyecto.dto.NotificacionRequestDto;
import itch.proyecto.dto.NotificacionResponseDto;
import itch.proyecto.service.NotificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/notificaciones")
@RequiredArgsConstructor
@Slf4j
public class NotificacionController {

    private final NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<NotificacionResponseDto> crearNotificacion(
            @Valid @RequestBody NotificacionRequestDto requestDto) {
        NotificacionResponseDto response = notificacionService.crearNotificacion(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponseDto> obtenerNotificacionPorId(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.obtenerNotificacionPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<NotificacionResponseDto>> listarTodasLasNotificaciones() {
        return ResponseEntity.ok(notificacionService.listarTodasLasNotificaciones());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificacionResponseDto>> listarPorUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.listarNotificacionesPorUsuarioId(usuarioId));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<NotificacionResponseDto> actualizarEstado(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarEstadoNotificacionDto dto) {
        return ResponseEntity.ok(notificacionService.actualizarEstado(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable Long id) {
        notificacionService.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }
}