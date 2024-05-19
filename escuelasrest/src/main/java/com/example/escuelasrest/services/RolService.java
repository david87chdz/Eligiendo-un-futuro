package com.example.escuelasrest.services;

import com.example.escuelasrest.entities.Rol;
import com.example.escuelasrest.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolService {
    @Autowired
    private RolRepository repository;

    /**
     * This method is used to find a Rol instance by its tipo.
     * It returns an Optional that can contain the Rol instance if it exists.
     * @param rolTipo The tipo of the Rol instance to be found. String.
     * @return An Optional that can contain the Rol instance if it exists.
     */
    public Optional<Rol> findByTipo(String rolTipo) {
        return repository.findByTipo(rolTipo);
    }
}
