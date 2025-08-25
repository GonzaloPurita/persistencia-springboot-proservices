package ar.utn.ba.ddsi.proservices.services;

import ar.utn.ba.ddsi.proservices.dtos.input.ServicioInputDTO;
import ar.utn.ba.ddsi.proservices.dtos.input.TareaInputDTO;
import ar.utn.ba.ddsi.proservices.dtos.output.ServicioOutputDTO;
import ar.utn.ba.ddsi.proservices.dtos.output.TareaOutputDTO;

import java.util.List;

public interface IServiciosService {
    List<ServicioOutputDTO> buscarTodos();
    Long agregarServicio(ServicioInputDTO servicioInputDTO);
    List<TareaOutputDTO> agregarTareasAServicio(Long servicioId, List<TareaInputDTO> tareasInputDTO);
    List<TareaOutputDTO> obtenerTareasDeServicio(Long servicioId);
}
