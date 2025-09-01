package ar.utn.ba.ddsi.proservices.models.entities.servicios;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tarea")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY) // La relacion ManyToOne es la que genera una columna, caso contrario con la OneToMany, porque justamente, la FK va a estar en la Tarea, la cual apunta a Servicio.
    @JoinColumn(name = "servicio_id", nullable = false) // Con JoinColumn estoy setteando que hay una constrain de FK a la tabla Servicio, y que la columna se llama ¨servicio_id¨.
                                                        // Esto lo hace el ORM por detras, y sabe donde tiene que apuntar porque por defecto apunta a la PK de la otra tabla, pero tambien le podriamos especificar a donde apuntar.
    private Servicio servicio;
}
