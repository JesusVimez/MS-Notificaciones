package itch.proyecto.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estados_notificacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoNotificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre;
}