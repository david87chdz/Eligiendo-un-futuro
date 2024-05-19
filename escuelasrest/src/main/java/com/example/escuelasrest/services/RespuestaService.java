package com.example.escuelasrest.services;

import com.example.escuelasrest.entities.Respuesta;
import com.example.escuelasrest.repositories.RespuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RespuestaService {
    @Autowired
    private RespuestaRepository repository;

/**
 * This method is used to retrieve all Respuesta instances from the repository.
 * It returns a List of all Respuesta instances.
 *
 * @return A List of all Respuesta instances.
 */
public List<Respuesta> getAllAnswers() {
    return repository.findAll();
}

/**
 * This method is used to find a Respuesta instance by its id.
 * It returns an Optional that can contain the Respuesta instance if it exists.
 *
 * @param id The id of the Respuesta instance to be found. This should be a Long.
 * @return An Optional that can contain the Respuesta instance if it exists.
 */
public Optional<Respuesta>  getAnswerById(Long id) {
    return repository.findById(id);
}

    /**
     * This method is used to create a new Respuesta instance.
     * It sets the current date and time as the fecha of the Respuesta instance and saves it to the repository.
     * The saved Respuesta instance is then returned.
     * @param respuesta The Respuesta instance to be saved. Instance of Respuesta.
     * @return The saved Respuesta instance.
     */
    public Respuesta createAnswer(Respuesta respuesta) {
        LocalDateTime fecha = LocalDateTime.now();
        respuesta.setFecha(fecha);
        return repository.save(respuesta);
    }

    /**
     * This method is used to update a Respuesta instance in the repository.
     * It takes a Respuesta instance as a parameter and saves it to the repository.
     * @param respuesta The updated Respuesta instance. Instance of Respuesta.
     */
    public void updateAnswer(Respuesta respuesta) {
        repository.save(respuesta);
    }

    /**
     * This method is used to delete a Respuesta instance from the repository by its id.
     * It takes the id of the Respuesta instance as a parameter and deletes the corresponding instance from the repository.
     * @param id The id of the Respuesta instance to be deleted. Long.
     */
    public void deleteAnswer(Long id) {
        repository.deleteById(id);
    }

    /**
     * This method is used to retrieve all Respuesta instances associated with a specific Comentario.
     * It returns a List of Respuesta instances that are associated with the Comentario with the provided id.
     * @param commentId The id of the Comentario whose associated Respuesta instances are to be found. Long.
     * @return A List of Respuesta instances that are associated with the Comentario with the provided id.
     */
    public List<Respuesta> getAnswersByCommentId(Long commentId) {
        return repository.findByComentarioId(commentId);
    }
}
