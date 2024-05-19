package com.example.escuelasrest.repositories;

import com.example.escuelasrest.entities.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActividadRepository extends JpaRepository <Actividad, Long> {

   /**
    * This method is used to find an Actividad  by its id.
    * It returns an Optional that can contain the Actividad if it exists.
    * @param id The id of the Actividad to be found.
    * @return An Optional that can contain the Actividad if it exists.
    */
   Optional<Actividad> findById(Long id);
}
