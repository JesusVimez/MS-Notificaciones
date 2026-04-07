package itch.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionRequestDto {

    @NotNull(message = "El usuarioId es obligatorio")
    private Long usuarioId;

    private Long incidenciaId;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    @NotNull(message = "El estadoId es obligatorio")
    private Long estadoId;
}