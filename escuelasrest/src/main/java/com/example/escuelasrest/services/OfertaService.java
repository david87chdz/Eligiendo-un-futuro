package com.example.escuelasrest.services;

import com.example.escuelasrest.entities.Oferta;
import com.example.escuelasrest.repositories.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfertaService {
    @Autowired
    private OfertaRepository repository;

    /**
     * This method is used to create a new Oferta instance.
     * It takes an Oferta instance as a parameter and saves it to the repository.
     * @param oferta The Oferta instance to be saved. Instance of Oferta.
     */
    public void createOferta(Oferta oferta) {
        repository.save(oferta);
    }

    /**
     * This method is used to find all Oferta instances associated with a specific Actividad.
     * It returns a List of Oferta instances that are associated with the Actividad with the provided id.
     * @param id The id of the Actividad whose associated Oferta instances are to be found. Number.
     * @return A List of Oferta instances that are associated with the Actividad with the provided id.
     */
    public List<Oferta> findByActividadId(Number id) {
        return repository.findByActividadId(id);
    }

    /**
     * This method is used to save an Oferta instance to the repository.
     * It takes an Oferta instance as a parameter and saves it to the repository.
     * @param oferta The Oferta instance to be saved. Instance of Oferta.
     */
    public void save(Oferta oferta) {
        repository.save(oferta);
    }

    /**
     * This method is used to find an Oferta instance associated with a specific Colegio and Actividad.
     * It returns an Optional that can contain the Oferta instance if it exists.
     * @param colegioId The id of the Colegio whose associated Oferta instance is to be found. Long.
     * @param actividadId The id of the Actividad whose associated Oferta instance is to be found. Long.
     * @return An Optional that can contain the Oferta instance if it exists.
     */
    public Optional<Oferta> findByColegioIdAndActividadId(Long colegioId, Long actividadId) {
        return repository.findByColegioIdAndActividadId(colegioId, actividadId);
    }

    /**
     * This method is used to find all Oferta instances associated with a specific Colegio.
     * It returns a List of Oferta instances that are associated with the Colegio with the provided id.
     * @param id The id of the Colegio whose associated Oferta instances are to be found. Long.
     * @return A List of Oferta instances that are associated with the Colegio with the provided id.
     */
    public List<Oferta> findByColegioId(Long id) {
        return repository.findByColegioId(id);
    }

    /**
     * This method is used to delete an Oferta instance from the repository.
     * It takes an Oferta instance as a parameter and deletes it from the repository.
     * @param oferta The Oferta instance to be deleted. Instance of Oferta.
     */
    public void delete(Oferta oferta) {
        repository.delete(oferta);
    }

    /**
 * This method is used to retrieve all Oferta instances from the repository.
 * It returns a List of all Oferta instances.
 *
 * @return A List of all Oferta instances.
 */
public List<Oferta> getAllOfertas() {
    return repository.findAll();
}
}
