package itch.proyecto.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionRequestDto {

    @NotNull(message = "La incidenciaId es obligatoria")
    private Long incidenciaId;

    @NotNull(message = "El estadoId es obligatorio")
    private Long estadoId;

    private String mensaje;  // El mensaje puede ser opcional
}