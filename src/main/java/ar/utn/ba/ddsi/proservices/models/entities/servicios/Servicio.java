package ar.utn.ba.ddsi.proservices.models.entities.servicios;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "servicio")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servicio")
    private List<Tarea> tareas;

    public void agregarTareas(Tarea ...tareas) {
        Collections.addAll(this.tareas, tareas);
        for(Tarea tarea: tareas) {
            tarea.setServicio(this);
        }
    }
}
