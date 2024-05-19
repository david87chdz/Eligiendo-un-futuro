package com.example.escuelasrest.services;

import com.example.escuelasrest.entities.Comentario;
import com.example.escuelasrest.repositories.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class ComentarioService {
    @Autowired
    private ComentarioRepository repository;

    /**
     * This method is used to retrieve all Comentario instances from the repository.
     * It returns a List of all Comentario instances.
     * @return A List of all Comentario instances.
     */
    public List<Comentario> getAllComments() {
        return repository.findAll();
    }

    /**
     * This method is used to find a Comentario instance by its id.
     * It returns an Optional that can contain the Comentario instance if it exists.
     * @param id The id of the Comentario instance to be found. Long.
     * @return An Optional that can contain the Comentario instance if it exists.
     */
    public Optional<Comentario> getCommentById(Long id) {
        return repository.findById(id);
    }

    /**
     * This method is used to create a new Comentario instance.
     * It sets the current date and time as the fecha of the Comentario instance and saves it to the repository.
     * The saved Comentario instance is then returned.
     * @param comentario The Comentario instance to be saved. Instance of Comentario.
     * @return The saved Comentario instance.
     */
    public Comentario createComment(Comentario comentario) {
        LocalDateTime fecha = LocalDateTime.now();
        comentario.setFecha(fecha);
        return repository.save(comentario);
    }

    /**
     * This method is used to update a Comentario instance in the repository.
     * It takes a Comentario instance as a parameter and saves it to the repository.
     * @param id The id of the Comentario instance to be updated. This should be a Long.
     * @param comentario The updated Comentario instance. Instance of Comentario.
     */
    public void updateComment(Long id, Comentario comentario) {
        repository.save(comentario);
    }

    /**
     * This method is used to delete a Comentario instance from the repository by its id.
     * It takes the id of the Comentario instance as a parameter and deletes the corresponding instance from the repository.
     * @param id The id of the Comentario instance to be deleted. Long.
     */
    public void deleteComment(Long id) {
        repository.deleteById(id);
    }

    /**
     * This method is used to retrieve all Comentario instances associated with a specific Colegio.
     * It returns a List of Comentario instances that are associated with the Colegio with the provided id.
     * @param schoolId The id of the Colegio whose associated Comentario instances are to be found. Long.
     * @return A List of Comentario instances that are associated with the Colegio with the provided id.
     */
    public List<Comentario> getCommentsBySchoolId(Long schoolId) {
        return repository.findByColegioId(schoolId);
    }

    /**
     * This method is used to retrieve all Comentario instances associated with a specific Usuario.
     * It returns a List of Comentario instances that are associated with the Usuario with the provided id.
     * @param id The id of the Usuario whose associated Comentario instances are to be found. Long.
     * @return A List of Comentario instances that are associated with the Usuario with the provided id.
     */
    public List<Comentario> getCommentsByUserId(Long id) {
        return repository.findByUsuarioId(id);
    }

    /**
     * This method is used to save a Comentario instance to the repository.
     * It takes a Comentario instance as a parameter and saves it to the repository.
     * @param comentario The Comentario instance to be saved. Instance of Comentario.
     */
    public void save(Comentario comentario) {
        repository.save(comentario);
    }
}
