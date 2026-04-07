package itch.proyecto.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActualizarEstadoNotificacionDto {

    @NotNull(message = "El estadoId es obligatorio")
    private Long estadoId;
}