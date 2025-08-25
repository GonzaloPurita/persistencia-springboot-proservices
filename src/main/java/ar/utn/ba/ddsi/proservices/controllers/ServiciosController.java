package ar.utn.ba.ddsi.proservices.controllers;

import ar.utn.ba.ddsi.proservices.dtos.input.ServicioInputDTO;
import ar.utn.ba.ddsi.proservices.dtos.input.TareaInputDTO;
import ar.utn.ba.ddsi.proservices.dtos.output.ServicioOutputDTO;
import ar.utn.ba.ddsi.proservices.dtos.output.TareaOutputDTO;
import ar.utn.ba.ddsi.proservices.services.IServiciosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicios")
public class ServiciosController {
    private final IServiciosService serviciosService;

    public ServiciosController(IServiciosService serviciosService) {
        this.serviciosService = serviciosService;
    }

    @GetMapping
    public ResponseEntity<List<ServicioOutputDTO>> obtenerTodosLosServicios() {
        List<ServicioOutputDTO> servicios = serviciosService.buscarTodos();
        return ResponseEntity.ok(servicios);
    }

    @PostMapping
    public ResponseEntity<Long> crearServicio(@RequestBody ServicioInputDTO servicioInputDTO) {
        Long servicioId = serviciosService.agregarServicio(servicioInputDTO);
        return ResponseEntity.ok(servicioId);
    }

    @PostMapping("/{id}/tareas")
    public ResponseEntity<List<TareaOutputDTO>> crearTareasParaServicio(
            @PathVariable("id") Long servicioId,
            @RequestBody List<TareaInputDTO> tareasInputDTO) {
        List<TareaOutputDTO> tareasCreadas = serviciosService.agregarTareasAServicio(servicioId, tareasInputDTO);
        return ResponseEntity.ok(tareasCreadas);
    }

    @GetMapping("/{id}/tareas")
    public ResponseEntity<List<TareaOutputDTO>> obtenerTareasDeServicio(@PathVariable("id") Long servicioId) {
        List<TareaOutputDTO> tareas = serviciosService.obtenerTareasDeServicio(servicioId);
        return ResponseEntity.ok(tareas);
    }
}
