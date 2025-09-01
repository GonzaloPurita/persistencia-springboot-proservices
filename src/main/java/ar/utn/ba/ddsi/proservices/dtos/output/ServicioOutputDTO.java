package ar.utn.ba.ddsi.proservices.dtos.output;

import ar.utn.ba.ddsi.proservices.models.entities.servicios.Servicio;
import lombok.Data;

@Data
public class ServicioOutputDTO {
    private Long id;
    private String nombre;

    public static ServicioOutputDTO from(Servicio servicio) { // metodo para recrear una instancia de outputDTO a partir de un servicio
        var dto = new ServicioOutputDTO();
        dto.setId(servicio.getId());
        dto.setNombre(servicio.getNombre());
        return dto;
    }
}
