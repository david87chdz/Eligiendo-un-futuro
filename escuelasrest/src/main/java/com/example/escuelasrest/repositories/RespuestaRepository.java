package com.example.escuelasrest.repositories;

import com.example.escuelasrest.entities.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RespuestaRepository extends JpaRepository <Respuesta, Long> {

    /**
     * This method is used to find all Respuesta associated with a specific Comentario.
     * It returns a List of Respuesta that are associated with the Comentario with the id.
     * @param commentId The id of the Comentario whose associated Respuesta are to be found. Long.
     * @return A List of Respuesta that are associated with the Comentario with the id.
     */
    public List<Respuesta> findByComentarioId(Long commentId);

    /**
     * This method is used to find a Respuesta by its id.
     * It returns an Optional that can contain the Respuesta if it exists.
     * @param id The id of the Respuesta to be found. Long.
     * @return An Optional that can contain the Respuesta if it exists.
     */
    Optional<Respuesta> findById(Long id);
}
