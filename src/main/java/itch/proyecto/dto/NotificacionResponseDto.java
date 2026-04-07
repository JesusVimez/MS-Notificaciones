package itch.proyecto.dto;

import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionResponseDto {

    private Long id;
    private Long usuarioId;
    private Long incidenciaId;
    private String mensaje;
    private LocalDateTime fecha;
    private Long estadoId;
    private String estadoNombre;
}