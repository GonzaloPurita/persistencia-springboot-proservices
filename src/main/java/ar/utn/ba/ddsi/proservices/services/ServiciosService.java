package ar.utn.ba.ddsi.proservices.services;

import ar.utn.ba.ddsi.proservices.dtos.input.ServicioInputDTO;
import ar.utn.ba.ddsi.proservices.dtos.input.TareaInputDTO;
import ar.utn.ba.ddsi.proservices.dtos.output.ServicioOutputDTO;
import ar.utn.ba.ddsi.proservices.dtos.output.TareaOutputDTO;
import ar.utn.ba.ddsi.proservices.models.entities.servicios.Servicio;
import ar.utn.ba.ddsi.proservices.models.entities.servicios.Tarea;
import ar.utn.ba.ddsi.proservices.models.repositories.IServicioRepository;
import ar.utn.ba.ddsi.proservices.models.repositories.ITareaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiciosService implements IServiciosService {
    private IServicioRepository servicioRepository;
    private ITareaRepository tareaRepository;

    public ServiciosService(IServicioRepository servicioRepository, ITareaRepository tareaRepository) {
        this.servicioRepository = servicioRepository;
        this.tareaRepository = tareaRepository;
    }

    @Override
    public List<ServicioOutputDTO> buscarTodos() {
        List<Servicio> servicios = this.servicioRepository.findAll(); //metodo de la JpaRepository
        return servicios.stream().map(ServicioOutputDTO::from).toList(); // mappeamos cada servicio, lo convertimos a dto y lo devolvermos como lista
    } // El mecanismo de hidratacion funcion cuando nosotros llamamos al findall del repositorio, el repositorio como sabe que es de la clase Servicio, va a buscar el nombre de la tabla asociada
    // entonces va a ir a dicha tabla en la base de datos, y ejecuta la consulta sql para devolver los datos que encontro.
    // Pero no va a devovler las filas de la tabla, va a instanciar las entidades de nuestro domonio con las filas que encontro. Esta transformacion o hidratacion la hace el ORM.
    // Este es el momento de populacion de objetos, agarramos los valores que vinieron en esa consulta, y los seteamos en las instancias de nuestro dominio.

    @Override
    public Long agregarServicio(ServicioInputDTO servicioInputDTO) {
        Servicio nuevoServicio = new Servicio();
        nuevoServicio.setNombre(servicioInputDTO.getNombre());

        this.servicioRepository.save(nuevoServicio); // el ORM se encarga del INSERT y de settear el id
        // el metodo save es transaccional, quiere decir que si falla va a hacer un rollback (nunca se inserta) automaticamente para volver a un estado consistente, y lanza una exception

        return nuevoServicio.getId();
    }

    @Override
    public List<TareaOutputDTO> agregarTareasAServicio(Long servicioId, List<TareaInputDTO> tareasInputDTO) {
        Servicio servicio = this.servicioRepository.findById(servicioId)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + servicioId));

        List<Tarea> tareas = tareasInputDTO.stream()
                .map(tareaInput -> {
                    Tarea tarea = new Tarea();
                    tarea.setDescripcion(tareaInput.getDescripcion());
                    tarea.setServicio(servicio);
                    return tarea;
                })
                .toList();

        List<Tarea> tareasGuardadas = this.tareaRepository.saveAll(tareas);

        return tareasGuardadas.stream()
                .map(TareaOutputDTO::from)
                .toList();
    }

    @Override
    public List<TareaOutputDTO> obtenerTareasDeServicio(Long servicioId) {
        Servicio servicio = this.servicioRepository.findById(servicioId)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + servicioId));

        return servicio.getTareas().stream()
                .map(TareaOutputDTO::from)
                .toList();
    }
}
