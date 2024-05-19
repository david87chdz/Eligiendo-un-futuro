package com.example.escuelasrest.repositories;

import com.example.escuelasrest.entities.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValoracionRepository extends JpaRepository <Valoracion, Long> {

    /**
     * This method is used to find all Valoracion associated with a specific Colegio.
     * It returns a List of Valoracion that are associated with the Colegio with the id.
     * @param colegioId The id of the Colegio whose associated Valoracion  are to be found. Long.
     * @return A List of Valoracion that are associated with the Colegio with the id.
     */
    List<Valoracion> findByColegioId(Long colegioId);

    List<Valoracion> findByUsuarioId(Long id);
}
