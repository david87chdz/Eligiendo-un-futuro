package com.example.escuelasrest.services;

import com.example.escuelasrest.entities.Actividad;
import com.example.escuelasrest.entities.Colegio;
import com.example.escuelasrest.entities.Oferta;
import com.example.escuelasrest.repositories.ColegioRepository;
import com.example.escuelasrest.repositories.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ColegioService {
    @Autowired
    private ColegioRepository repository;

    @Autowired
    private OfertaRepository ofertaRepository;

    /**
     * This method is used to retrieve all Colegio instances from the repository.
     * It returns a List of all Colegio instances.
     * @return A List of all Colegio instances.
     */
    public List<Colegio> findAll(){
        return repository.findAll();
    }

    /**
     * This method is used to find a Colegio instance by its id.
     * It returns an Optional that can contain the Colegio instance if it exists.
     * @param id The id of the Colegio instance to be found. Long.
     * @return An Optional that can contain the Colegio instance if it exists.
     */
    public Optional<Colegio> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * This method is used to retrieve all Actividad instances associated with a specific Colegio.
     * It returns a List of Actividad instances that are associated with the Colegio with the provided id.
     * @param colegioId The id of the Colegio whose associated Actividad instances are to be found. Long.
     * @return A List of Actividad instances that are associated with the Colegio with the provided id.
     */
    public List<Actividad> getActividadesByColegioId(Long colegioId) {
        List<Oferta> ofertas = ofertaRepository.findByColegioId(colegioId);
        return ofertas.stream()
                .map(Oferta::getActividad)
                .collect(Collectors.toList());
    }

    /**
     * This method is used to save a Colegio instance to the repository.
     * It takes a Colegio instance as a parameter and saves it to the repository.
     * The saved Colegio instance is then returned.
     * @param colegio The Colegio instance to be saved. Instance of Colegio.
     * @return The saved Colegio instance.
     */
    public Colegio save(Colegio colegio) {
        return repository.save(colegio);
    }

    /**
     * This method is used to find a Colegio instance by its email.
     * It returns an Optional that can contain the Colegio instance if it exists.
     * @param email The email of the Colegio instance to be found. String.
     * @return An Optional that can contain the Colegio instance if it exists.
     */
    public List<Colegio> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    /**
     * This method is used to delete a Colegio instance from the repository.
     * It takes a Colegio instance as a parameter and deletes it from the repository.
     * @param colegio The Colegio instance to be deleted. Instance of Colegio.
     */
    public void delete(Colegio colegio) {
        repository.delete(colegio);
    }

    /**
     * This method is used to delete a Colegio instance from the repository by its id.
     * It takes the id of the Colegio instance as a parameter and deletes the corresponding instance from the repository.
     * @param id The id of the Colegio instance to be deleted. Long.
     */
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Optional<Colegio> findByUsuarioId(Long id) {
        return repository.findByUsuarioId(id);
    }

    public List<Colegio> findByEmailAndActivo(String email, boolean b) {
        return repository.findByEmailAndActivo(email, b);
    }
}
