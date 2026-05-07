package itch.proyecto.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import itch.proyecto.dto.IncidenciaResponseDto;

// Cambiar el nombre del FeignClient para evitar el conflicto
@FeignClient(name = "incidencias-gateway-client", url = "http://26.227.33.49:9000")
public interface IncidenciaClient {

    @GetMapping("/api/incidencias/{id}")
    IncidenciaResponseDto obtenerIncidenciaPorId(@PathVariable Long id);
}