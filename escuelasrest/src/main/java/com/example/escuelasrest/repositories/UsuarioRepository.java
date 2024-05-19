package com.example.escuelasrest.repositories;

import com.example.escuelasrest.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

    /**
     * This method is used to find all Usuario along with their associated Rol types.
     * It returns a List of Object arrays where each array contains a Usuario and the type of its associated Rol.
     * @return A List of Object arrays where each array contains a Usuario and the type of its associated Rol.
     */
    @Query("SELECT u, r.tipo FROM Usuario u JOIN u.rol r")
    List<Object[]> findAllWithTipoRol();

    /**
     * This method is used to find all Usuario associated with a specific Rol type.
     * It returns a List of Usuario that are associated with the Rol with the type.
     * @param tipo The type of the Rol whose associated Usuario are to be found.
     * @return A List of Usuario that are associated with the Rol with the type.
     */
    List<Usuario> findByRol_Tipo(String tipo);

    /**
     * This method is used to find a Usuario by its email.
     * It returns an Optional that can contain the Usuario if it exists.
     * @param email The email of the Usuario to be found.
     * @return An Optional that can contain the Usuario if it exists.
     */
    List<Usuario> findByEmail(String email);

    /**
     * This method is used to find a Usuario by its id.
     * It returns an Optional that can contain the Usuario if it exists.
     * @param id The id of the Usuario to be found.
     * @return An Optional that can contain the Usuario if it exists.
     */
    Optional<Usuario> findById(Long id);

    List<Usuario> findByEmailAndActivo(String email, boolean b);
}
