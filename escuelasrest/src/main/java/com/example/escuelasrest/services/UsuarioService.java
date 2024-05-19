package com.example.escuelasrest.services;

import com.example.escuelasrest.entities.Usuario;
import com.example.escuelasrest.repositories.ColegioRepository;
import com.example.escuelasrest.repositories.UsuarioRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private ColegioRepository colegioRepository;

    @Autowired
    private EntityManager em;

   /**
     * This method is used to retrieve all Usuario instances from the repository.
     * It returns a List of all Usuario instances.
     * @return A List of all Usuario instances.
     */
    public List<Usuario> findAll() {
        return repository.findAll();
    }

    /**
     * This method is used to retrieve all Usuario instances with their associated Rol type from the repository.
     * It returns a List of Maps, where each Map contains a Usuario instance and its associated Rol type.
     * @return A List of Maps, where each Map contains a Usuario instance and its associated Rol type.
     */
    @GetMapping("/rol")
    public List<Map<String, Object>> findAllWithTipoRol() {
        List<Object[]> usuariosWithTipoRol = repository.findAllWithTipoRol();
        return usuariosWithTipoRol.stream()
                .map(objects -> {
                    Map<String, Object> usuarioMap = new HashMap<>();
                    usuarioMap.put("usuario", (Usuario) objects[0]);
                    usuarioMap.put("rol", (String) objects[1]); // Suponiendo que el tipo de rol es de tipo String
                    return usuarioMap;
                })
                .collect(Collectors.toList());
    }

    /**
     * This method is used to find a Usuario instance by its id.
     * It returns an Optional that can contain the Usuario instance if it exists.
     * @param id The id of the Usuario instance to be found. Long.
     * @return An Optional that can contain the Usuario instance if it exists.
     */
    public Optional<Usuario> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * This method is used to save a Usuario instance to the repository.
     * It takes a Usuario instance as a parameter and saves it to the repository.
     * The saved Usuario instance is then returned.
     * @param usuario The Usuario instance to be saved. Instance of Usuario.
     * @return The saved Usuario instance.
     */
    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }

    /**
     * This method is used to delete a Usuario instance from the repository by its id.
     * It takes the id of the Usuario instance as a parameter and deletes the corresponding instance from the repository.
     * @param id The id of the Usuario instance to be deleted. Long.
     */
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    /**
     * This method is used to authenticate a Usuario instance.
     * It takes an email and a password as parameters, and if a Usuario instance with the provided email and password exists in the repository,
     * it returns a Map containing the Usuario instance and its associated Rol type.
     * If no such Usuario instance exists, it returns null.
     * @param email The email of the Usuario instance to be authenticated. String.
     * @param password The password of the Usuario instance to be authenticated. String.
     * @return A Map containing the Usuario instance and its associated Rol type if such a Usuario instance exists, null otherwise.
     */
//    public Map<String, Object> authenticate(String email, String password) {
//        Usuario usuario = repository.findByEmail(email).orElse(null);
//        if (usuario != null && usuario.getPassword().equals(password)) {
//            Map<String, Object> response = new HashMap<>();
//            response.put("rol", usuario.getRol().getTipo());
//            response.put("usuario", usuario);
//            return response;
//        }
//        return null;
//    }

    /**
     * This method is used to find a Usuario instance by its email.
     * It returns an Optional that can contain the Usuario instance if it exists.
     * @param email The email of the Usuario instance to be found. String.
     * @return An Optional that can contain the Usuario instance if it exists.
     */
    public List<Usuario> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    /**
     * This method is used to find all Usuario instances that are not associated with a Colegio.
     * It returns a List of such Usuario instances.
     * @return A List of Usuario instances that are not associated with a Colegio.
     */
    public List<Usuario> findUserNotInColegio() {
        List<Usuario> users = repository.findByRol_Tipo("School");
        List<String> colegioEmails = colegioRepository.findAllEmails();
        return users.stream()
                .filter(user -> !colegioEmails.contains(user.getEmail()))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to delete a Usuario instance from the repository.
     * It takes a Usuario instance as a parameter and deletes it from the repository.
     * @param usuario The Usuario instance to be deleted. This should be an instance of Usuario.
     */
    public void delete(Usuario usuario) {
        repository.delete(usuario);
    }

    public List<Usuario> findByEmailAndActivo(String email, boolean b) {
        return repository.findByEmailAndActivo(email, b);
    }
}
