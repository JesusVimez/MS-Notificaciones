package itch.proyecto.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import itch.proyecto.dto.UsuarioResponseDto;

@FeignClient(name = "USUARIOS")
public interface UsuarioCliente {

    @GetMapping("/api/usuarios/{id}")
    UsuarioResponseDto obtenerUsuarioPorId(@PathVariable Long id);
}