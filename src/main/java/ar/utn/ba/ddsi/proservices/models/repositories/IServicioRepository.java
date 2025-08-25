package ar.utn.ba.ddsi.proservices.models.repositories;

import ar.utn.ba.ddsi.proservices.models.entities.servicios.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IServicioRepository extends JpaRepository<Servicio, Long> {
}
