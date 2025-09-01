package ar.utn.ba.ddsi.proservices.models.entities.servicios;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Entity // con esto decimos que es una entidad persistente
@Table(name = "servicio") // Con esta notacion digo que esta clase va a mappear en el mundo relacional, con la tabla "servicio". Si no le ponemos nombre a la tabla persistida, le va a poner "servicios"
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Servicio {
    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Con GeneratedValue va a ser una PK subrogada incremental. Con el IDENTITY le estamos delegando la tarea a la BD de gestionar los ids, la BD los va a poner autoincremental
    private Long id;

    @Column(nullable = false) // not null
    private String nombre; // el ORM (hibernate) va a pasar el string a un VARCHAR(255). Por defecto, la columna va a tener el mismo nombre que el atributo, pero se le puede cambiar con name = "nombreTabla".
                           // No es recomendable cambiar el nombre porque porque si el dia de mañana cambiamos el nombre del atributo, tambien hay que acordarse de cambiar el nombre de la columna, para mantener la consistencia.
                           // usando columnDefinition puedo hacer que columnDefinition = VARCHAR(50), "TEXT", etc. Le puedo cambiar el tipo.

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servicio") // relacion de tarea y servicio, visto desde servicio
                                                              // para hacer el mappeo entre entidades, tengo que especificar con mappedBy el atributo de la otra clase que me esta apuntando a mi (siendo mi, la clase donde estoy parado)
    // EXISTEN 3 POSIBILIDADES DE MAPPEAR LAS RELACIONES MANY_TO_ONE O ONE_TO_MANY
    // Ahora haremos la forma bidireccional, donde Servicio tiene una coleccion de tareas y Tarea tiene un Servicio
    private List<Tarea> tareas;

    // A NIVEL OBJETOS NUESTRAS ENTIDADES SON BIDIRECCIONALES, PERO A NIVEL DATOS EL SERVICIO ES UNA TABLA INDEPENDIENTE, que solo tiene id y nombre. Va a ser la tabla Tarea la que tenga la FK a Servicio.

    // En la relacion, en el fetch, con LAZY el ORM solo va a popular las relaciones cuando las necesite, es decir, si solo necesito ir a la tabla de servicios para un endpoint, no va a popular ninguna tarea
    // Si fuera EAGER en vez de LAZY, entonces popularia t0do, aunque no lo use. Carga el atributo ansiosamente con EAGER, siempre que va a la BD y lo recupera.

    public void agregarTareas(Tarea ...tareas) {
        Collections.addAll(this.tareas, tareas);
        for(Tarea tarea: tareas) {
            tarea.setServicio(this);
        }
    }
}
