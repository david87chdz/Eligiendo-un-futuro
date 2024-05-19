package com.example.escuelasrest.repositories;

import com.example.escuelasrest.entities.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfertaRepository extends JpaRepository <Oferta, Long> {

    /**
     * This method is used to find all Oferta associated with a specific Colegio.
     * It returns a List of Oferta that are associated with the Colegio with the id.
     * @param colegioId The id of the Colegio whose associated Oferta are to be found.
     * @return A List of Oferta that are associated with the Colegio with the id.
     */
    List<Oferta> findByColegioId(Long colegioId);

    /**
     * This method is used to find all Oferta associated with a specific Actividad.
     * It returns a List of Oferta that are associated with the Actividad with the id.
     * @param id The id of the Actividad whose associated Oferta are to be found.
     * @return A List of Oferta that are associated with the Actividad with  id.
     */
    List<Oferta> findByActividadId(Number id);

    /**
     * This method is used to find an Oferta associated with a specific Colegio and Actividad.
     * It returns an Optional that can contain the Oferta if it exists.
     * @param colegioId The id of the Colegio associated with the Oferta to be found.
     * @param actividadId The id of the Actividad associated with the Oferta to be found.
     * @return An Optional that can contain the Oferta if it exists.
     */
    Optional<Oferta> findByColegioIdAndActividadId(Long colegioId, Long actividadId);



}
