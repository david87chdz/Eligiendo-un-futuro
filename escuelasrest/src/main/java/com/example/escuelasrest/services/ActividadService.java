package com.example.escuelasrest.services;

import com.example.escuelasrest.entities.Actividad;
import com.example.escuelasrest.repositories.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActividadService {
    @Autowired
    private ActividadRepository repository;


    /**
     * This method is used to create a new Actividad instance.
     * It takes an Actividad instance as a parameter and saves it to the repository.
     * The saved Actividad instance is then returned.
     * @param actividad The Actividad instance to be saved. Instance of Actividad.
     * @return The saved Actividad instance.
     */
    public Actividad createActividad(Actividad actividad) {
        return repository.save(actividad);
    }

    /**
     * This method is used to retrieve all Actividad instances from the repository.
     * It returns a List of all Actividad instances.
     * @return A List of all Actividad instances.
     */
    public List<Actividad> getAllActivities() {
        return repository.findAll();
    }

    /**
     * This method is used to find an Actividad instance by its id.
     * It returns an Optional that can contain the Actividad instance if it exists.
     * @param actividadId The id of the Actividad instance to be found. Long.
     * @return An Optional that can contain the Actividad instance if it exists.
     */
    public Optional<Actividad> findById(Long actividadId) {
        return repository.findById(actividadId);
    }

    /**
     * This method is used to save an Actividad instance to the repository.
     * It takes an Actividad instance as a parameter and saves it to the repository.
     * @param actividad The Actividad instance to be saved. Instance of Actividad.
     */
    public void save(Actividad actividad) {
        repository.save(actividad);
    }
}
