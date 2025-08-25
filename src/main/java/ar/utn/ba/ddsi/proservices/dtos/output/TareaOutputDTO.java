package ar.utn.ba.ddsi.proservices.dtos.output;

import ar.utn.ba.ddsi.proservices.models.entities.servicios.Tarea;
import lombok.Data;

@Data
public class TareaOutputDTO {
    private Long id;
    private String descripcion;
    private Long servicioId;

    public static TareaOutputDTO from(Tarea tarea) {
        var dto = new TareaOutputDTO();
        dto.setId(tarea.getId());
        dto.setDescripcion(tarea.getDescripcion());
        dto.setServicioId(tarea.getServicio().getId());
        return dto;
    }
}
