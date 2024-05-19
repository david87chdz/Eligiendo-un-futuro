package com.example.escuelasrest.controller;

import com.example.escuelasrest.entities.Colegio;
import com.example.escuelasrest.entities.Rol;
import com.example.escuelasrest.entities.Usuario;
import com.example.escuelasrest.services.ColegioService;
import com.example.escuelasrest.services.RolService;
import com.example.escuelasrest.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private ColegioService colegioService;


    /**
     * This method is a POST request mapped to the root of the "/api/usuarios" endpoint.
     * It creates a new Usuario entity and saves it in the system.
     * @param data A Map containing the key-value pairs representing the fields of the Usuario entity.
     *             The keys should be "nombre", "apellidos", "email", "password", and "rol".
     *             "nombre", "apellidos", "email", and "password" should be Strings.
     *             "rol" should be a String representing the type of the Rol entity associated with the Usuario.
     * @return A ResponseEntity containing the saved Usuario entity and the status code.
     *         The status code is 201 (Created) if the operation is successful.
     *         If a Usuario with the same email already exists in the system, the status code is 409 (Conflict).
     * @throws RuntimeException If the Rol entity associated with the provided type cannot be found.
     */
    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody Map<String, Object> data) {
        Usuario usuario = new Usuario();
        usuario.setNombre((String) data.get("nombre"));
        usuario.setApellidos((String) data.get("apellidos"));
        usuario.setEmail((String) data.get("email"));
        usuario.setPassword((String) data.get("password"));
        usuario.setActivo(true);
        usuario.setDesactivaciones(0);
        String rolTipo = (String) data.get("rol");
        Rol rol = rolService.findByTipo(rolTipo)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con : " + rolTipo));
        usuario.setRol(rol);

        try {
            Usuario savedUsuario = usuarioService.save(usuario);
            return new ResponseEntity<>(savedUsuario, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
        }
    }

    /**
     * This method is a GET request mapped to the root of the "/api/usuarios" endpoint.
     * It retrieves all Usuario entities from the system.
     *
     * @return A List of Usuario entities. If no Usuario entities are found, this will be an empty list.
     */
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.findAll();
    }

    /*//Nigual eliminar
    @GetMapping("/rol")
    public List<Map<String, Object>> getAllUsuariosRol() {
        return usuarioService.findAllWithTipoRol();
    }*/


   /**
 * This method is a GET request mapped to "/api/usuarios/{id}".
 * It retrieves a Usuario instance identified by its id.
 *
 * It first finds the Usuario instance by its id.
 * If the Usuario instance is not found, it returns a 404 Not Found status.
 * If the Usuario instance is found, it returns a 200 OK status with the Usuario instance.
 *
 * @param id The id of the Usuario instance to be retrieved. This should be a Long.
 * @return A ResponseEntity that contains the Usuario instance if it is found.
 *         If the Usuario instance is not found, it returns a 404 Not Found status.
 */
@GetMapping("/{id}")
public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
    Optional<Usuario> usuarioOptional = usuarioService.findById(id);
    return usuarioOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
}



    /**
     * This method is a GET request mapped to "/api/usuarios/newUsers".
     * It retrieves all Usuario entities that are not associated with a Colegio.
     *
     * @return A List of Usuario entities that are not associated with a Colegio.
     *         If no such Usuario entities are found, this will be an empty list.
     */
    @GetMapping("/newUsers")
    public List<Usuario> getUsersNotInColegio() {
        return usuarioService.findUserNotInColegio();
    }

    /**
     * This method is a PUT request mapped to "/api/usuarios/{id}".
     * It updates an existing Usuario entity identified by its ID.
     *
     * @param id The ID of the Usuario to be updated. This should be a Long.
     * @param data A Map containing the key-value pairs representing the fields to be updated.
     *             The keys can be "nombre", "apellidos", and "password".
     *             "nombre", "apellidos", and "password" should be Strings.
     * @return The updated Usuario entity.
     * @throws RuntimeException If the Usuario associated with the provided ID cannot be found.
     */
    @PutMapping("/{id}")
    public Usuario updateUsuario(@PathVariable Long id, @RequestBody Map<String, Object> data) {
        Optional<Usuario> optionalUsuario = usuarioService.findById(id);
        if (!optionalUsuario.isPresent()) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        Usuario usuario = optionalUsuario.get();
        if (data.containsKey("nombre")) {
            usuario.setNombre((String) data.get("nombre"));
        }
        if (data.containsKey("apellidos")) {
            usuario.setApellidos((String) data.get("apellidos"));
        }
        if (data.containsKey("password")) {
            usuario.setPassword((String) data.get("password"));
        }
        return usuarioService.save(usuario);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteById(id);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
            SQLIntegrityConstraintViolationException sqlException = (SQLIntegrityConstraintViolationException) e.getCause();
            if (sqlException.getMessage().contains("Duplicate entry")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }


  @PutMapping("/reactivate/{id}")
public ResponseEntity<?> reactivateUsuario(@PathVariable Long id) {
    Usuario usuario = usuarioService.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

    // Verificar si existe algún usuario activo con el mismo correo electrónico
    List<Usuario> usuariosActivos = usuarioService.findByEmailAndActivo(usuario.getEmail(), true);
    if (!usuariosActivos.isEmpty()) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un usuario activo con el correo electrónico: " + usuario.getEmail());
    }

    usuario.setActivo(true);
    Optional<Colegio> colegioOptional = colegioService.findByUsuarioId(id);
    if (colegioOptional.isPresent()) {
        Colegio colegio = colegioOptional.get();
        colegio.setActivo(true);
        colegioService.save(colegio);
    }
    usuarioService.save(usuario);
    return ResponseEntity.ok(Collections.singletonMap("message", "Usuario y colegio asociado reactivados exitosamente"));
}

    /*Este metodo funciona
    @PutMapping("/deactivate/{email}")
    public ResponseEntity<?> deactivateUsuario(@PathVariable String email) {
        Usuario usuario = usuarioService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
        usuario.setActivo(false);
        usuarioService.save(usuario);
        return ResponseEntity.ok(Collections.singletonMap("message", "Usuario desactivado exitosamente"));
    }*/

    @PutMapping("/deactivate/{email}")
    public ResponseEntity<?> deactivateUsuario(@PathVariable String email) {
        List<Usuario> usuarios = usuarioService.findByEmail(email);
        if (usuarios.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado con email: " + email);
        }
        usuarios.forEach(usuario -> {
            usuario.setDesactivaciones(usuario.getDesactivaciones() + 1);
            if (usuario.isActivo()) {
                usuario.setActivo(false);
            }
            usuarioService.save(usuario);
            usuario.getColegios().forEach(colegio -> {
                if (colegio.isActivo()) {
                    colegio.setActivo(false);
                    colegioService.save(colegio);
                }
            });
        });
        return ResponseEntity.ok(Collections.singletonMap("message", "Usuarios y colegios asociados desactivados exitosamente"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUsuarioAndColegioByUserId(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        Optional<Colegio> colegioOptional = colegioService.findByUsuarioId(id);
        if (colegioOptional.isPresent()) {
            Colegio colegio = colegioOptional.get();
            colegioService.delete(colegio);
        }
        usuarioService.delete(usuario);
        return ResponseEntity.ok(Collections.singletonMap("message", "Usuario y colegio eliminados exitosamente"));
    }
}
