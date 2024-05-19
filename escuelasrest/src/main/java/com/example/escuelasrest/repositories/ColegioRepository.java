package com.example.escuelasrest.repositories;

import com.example.escuelasrest.entities.Colegio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColegioRepository extends JpaRepository <Colegio, Long> {

    /**
     * This method is used to find a Colegio by its id.
     * @param id The id of the Colegio to be found.
     * @return An Optional that can contain the Colegio if it exists.
     */
    Optional<Colegio> findById(Long id);

    /**
     * This method is used to find a Colegio by its email.
     * @param email The email of the Colegio to be found.
     * @return An Optional that can contain the Colegio if it exists.
     */
    List<Colegio> findByEmail(String email);

    /**
     * This method is used to find all emails of Colegio .
     * @return A List of Strings that contains the emails of all Colegio.
     */
    @Query("SELECT c.email FROM Colegio c")
    List<String> findAllEmails();

    /**
     * This method is used to find a Colegio by its associated Usuario's id.
     * @param id The id of the Usuario associated with the Colegio to be found.
     * @return An Optional that can contain the Colegio if it exists.
     */
    Optional<Colegio> findByUsuarioId(Long id);

    /**
     * This method is used to find all Colegio  by their email and active status.
     * @param email The email of the Colegio  to be found.
     * @param b The active status of the Colegio  to be found.
     * @return A List of Colegio  that match the given email and active status.
     */
    List<Colegio> findByEmailAndActivo(String email, boolean b);
}
