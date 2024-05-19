package com.example.escuelasrest.repositories;

import com.example.escuelasrest.entities.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
   public interface ComentarioRepository extends JpaRepository <Comentario, Long> {

    /**
     * This method is used to find all Comentario associated with a specific Colegio.
     * It returns a List of Comentario that are associated with the Colegio with the provided id.
     * @param schoolId The id of the Colegio whose associated Comentario are to be found. Long.
     * @return A List of Comentario that are associated with the Colegio with the provided id.
     */
    List<Comentario> findByColegioId(Long schoolId);

    /**
     * This method is used to find all Comentario associated with a specific Usuario.
     * It returns a List of Comentario that are associated with the Usuario with the provided id.
     * @param id The id of the Usuario whose associated Comentario are to be found. Long.
     * @return A List of Comentario that are associated with the Usuario with the provided id.
     */
    List<Comentario> findByUsuarioId(Long id);
}
