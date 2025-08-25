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
        List<Servicio> servicios = this.servicioRepository.findAll();
        return servicios.stream().map(ServicioOutputDTO::from).toList();
    }

    @Override
    public Long agregarServicio(ServicioInputDTO servicioInputDTO) {
        Servicio nuevoServicio = new Servicio();
        nuevoServicio.setNombre(servicioInputDTO.getNombre());

        this.servicioRepository.save(nuevoServicio);

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
