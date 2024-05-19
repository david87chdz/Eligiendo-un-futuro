package com.example.escuelasrest.repositories;

import com.example.escuelasrest.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository <Rol, Long> {
    /**
     * This method is used to find a Rol by its type.
     * It returns an Optional that can contain the Rol if it exists.
     * @param rolTipo The type of the Rol instance to be found.
     * @return An Optional that can contain the Rol if it exists.
     */
    Optional<Rol> findByTipo(String rolTipo);
}
