package com.example.escuelasrest.services;

import com.example.escuelasrest.entities.Valoracion;
import com.example.escuelasrest.repositories.ValoracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ValoracionService {
    @Autowired
    private ValoracionRepository repository;

    /**
     * This method is used to retrieve all Valoracion instances from the repository.
     * It returns a List of all Valoracion instances.
     * @return A List of all Valoracion instances.
     */
    public List<Valoracion> getAllRatings() {
        return repository.findAll();
    }

    /**
     * This method is used to find a Valoracion instance by its id.
     * It returns an Optional that can contain the Valoracion instance if it exists.
     * @param id The id of the Valoracion instance to be found. Long.
     * @return An Optional that can contain the Valoracion instance if it exists.
     */
    public Optional<Valoracion> getRatingById(Long id) {
        return repository.findById(id);
    }

    /**
     * This method is used to create a new Valoracion instance.
     * It takes a Valoracion instance as a parameter and saves it to the repository.
     * The saved Valoracion instance is then returned.
     * @param valoracion The Valoracion instance to be saved. Instance of Valoracion.
     * @return The saved Valoracion instance.
     */
    public Valoracion createRating(Valoracion valoracion) {
        return repository.save(valoracion);
    }

    /**
     * This method is used to update a Valoracion instance in the repository.
     * It takes a Valoracion instance as a parameter and saves it to the repository.
     * @param id The id of the Valoracion instance to be updated. This should be a Long.
     * @param valoracion The updated Valoracion instance. This should be an instance of Valoracion.
     */
    public void updateRating(Long id, Valoracion valoracion) {
        repository.save(valoracion);
    }

    /**
     * This method is used to delete a Valoracion instance from the repository by its id.
     * It takes the id of the Valoracion instance as a parameter and deletes the corresponding instance from the repository.
     * @param id The id of the Valoracion instance to be deleted. Long.
     */
    public void deleteRating(Long id) {
        repository.deleteById(id);
    }

    /**
     * This method is used to retrieve all Valoracion instances associated with a specific Colegio.
     * It returns a List of Valoracion instances that are associated with the Colegio with the provided id.
     * @param schoolId The id of the Colegio whose associated Valoracion instances are to be found. Long.
     * @return A List of Valoracion instances that are associated with the Colegio with the provided id.
     */
    public List<Valoracion> getRatingsBySchoolId(Long schoolId) {
        return repository.findByColegioId(schoolId);
    }

    /**
     * This method is used to retrieve all Valoracion instances associated with a specific Colegio.
     * It returns a List of Valoracion instances that are associated with the Colegio with the provided id.
     * @param id The id of the Colegio whose associated Valoracion instances are to be found. Long.
     * @return A List of Valoracion instances that are associated with the Colegio with the provided id.
     */
    public List<Valoracion> findByColegioId(Long id) {
        return repository.findByColegioId(id);
    }

    public List<Valoracion> getValoracionesByUsuarioId(Long id) {
        return repository.findByUsuarioId(id);
    }
}
