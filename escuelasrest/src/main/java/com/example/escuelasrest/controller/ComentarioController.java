package com.example.escuelasrest.controller;

import com.example.escuelasrest.entities.Colegio;
import com.example.escuelasrest.entities.Comentario;
import com.example.escuelasrest.entities.Usuario;
import com.example.escuelasrest.services.ColegioService;
import com.example.escuelasrest.services.ComentarioService;
import com.example.escuelasrest.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {
    @Autowired
    private ComentarioService comentarioService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ColegioService colegioService;

    /**
     * This method is a GET request mapped to the root of the "/api/comentarios" endpoint.
     * It retrieves all comments in the system.
     *
     * @return A ResponseEntity containing a list of all comments.
     *         The status code is 200 (OK) if the operation is successful.
     */
    @GetMapping
    public ResponseEntity<List<Comentario>> getAllComments() {
        List<Comentario> comentarios = comentarioService.getAllComments();
        return ResponseEntity.ok(comentarios);
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<Comentario> getCommentById(@PathVariable Long id) {
        Optional<Comentario> comentario = comentarioService.getCommentById(id);
        return comentario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }*/

    /**
     * This method is a GET request mapped to "/colegio/{id}".
     * It retrieves all comments associated with a specific school identified by its ID.
     *
     * @param id The ID of the school for which comments are to be retrieved.
     * @return A ResponseEntity containing a list of comments associated with the school.
     *         The status code is 200 (OK) if the operation is successful.
     */
    @GetMapping("/colegio/{id}")
    public ResponseEntity<List<Comentario>> getCommentsBySchoolId(@PathVariable Long id) {
        List<Comentario> comentarios = comentarioService.getCommentsBySchoolId(id);
        return ResponseEntity.ok(comentarios);
    }

    /**
     * This method is a POST request mapped to the root of the "/api/comentarios" endpoint.
     * It creates a new comment in the system.
     *
     * @param data A map containing the data for the new comment. The map should contain the following keys:
     *             - "usuarioId": The ID of the user who is creating the comment. This should be an Integer.
     *             - "colegioId": The ID of the school that the comment is associated with. This should be an Integer.
     *             - "contenido": The content of the comment. This should be a String.
     * @return A ResponseEntity containing the newly created comment. The status code is 201 (Created) if the operation is successful.
     * @throws RuntimeException If the user or school associated with the provided IDs cannot be found.
     */
    @PostMapping
    public ResponseEntity<Comentario> createComment(@RequestBody Map<String, Object> data) {
        Long usuarioId = Long.valueOf((Integer) data.get("usuarioId"));
        Usuario usuario = usuarioService.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));
        Long colegioId = Long.valueOf((Integer) data.get("colegioId"));
        Colegio colegio = colegioService.findById(colegioId)
                .orElseThrow(() -> new RuntimeException("Colegio no encontrado con ID: " + colegioId));
        Comentario comentario = new Comentario();
        comentario.setContenido((String) data.get("contenido"));
        comentario.setUsuario(usuario);
        comentario.setColegio(colegio);
        comentario.setActivo(true);
        Comentario newComentario = comentarioService.createComment(comentario);
        return ResponseEntity.created(URI.create("/api/comentarios/" + newComentario.getId())).body(newComentario);
    }

    /**
     * This method is a PUT request mapped to "/api/comentarios/{id}".
     * It updates an existing comment in the system.
     *
     * @param id The ID of the comment to be updated. This should be a Long.
     * @param data A map containing the data for the comment to be updated. The map should contain the following key:
     *             - "contenido": The new content of the comment. This should be a String.
     * @return A ResponseEntity with a status code of 204 (No Content) if the operation is successful.
     *         If the comment associated with the provided ID cannot be found, the status code is 404 (Not Found).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateComment(@PathVariable Long id, @RequestBody Map<String, Object> data) {
        Optional<Comentario> comentarioOptional = comentarioService.getCommentById(id);
        if (comentarioOptional.isPresent()) {
            Comentario comentario = comentarioOptional.get();
            comentario.setContenido((String)data.get("contenido"));
            comentarioService.save(comentario);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * This method is a DELETE request mapped to "/api/comentarios/{id}".
     * It deletes an existing comment in the system.
     *
     * @param id The ID of the comment to be deleted. This should be a Long.
     * @return A ResponseEntity with a status code of 204 (No Content) if the operation is successful.
     *         If the comment associated with the provided ID cannot be found, the status code is 404 (Not Found).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        comentarioService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * This method is a GET request mapped to "/api/comentarios/usuario/{id}".
     * It retrieves all comments associated with a specific user identified by their ID.
     *
     * @param id The ID of the user for which comments are to be retrieved. This should be a Long.
     * @return A ResponseEntity containing a list of comments associated with the user.
     *         The status code is 200 (OK) if the operation is successful.
     */
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Comentario>> getCommentsByUserId(@PathVariable Long id) {
        List<Comentario> comentarios = comentarioService.getCommentsByUserId(id);
        return ResponseEntity.ok(comentarios);
    }

    /**
     * This method is a PUT request mapped to "/api/comentarios/{id}/activate".
     * It activates a comment that has been deactivated.
     *
     * @param id The ID of the comment to be activated. This should be a Long.
     * @return A ResponseEntity with a status code of 204 (No Content) if the operation is successful.
     *         If the comment associated with the provided ID cannot be found, the status code is 404 (Not Found).
     */
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateComment(@PathVariable Long id) {
        Optional<Comentario> comentarioOptional = comentarioService.getCommentById(id);
        if (comentarioOptional.isPresent()) {
            Comentario comentario = comentarioOptional.get();

            comentario.setActivo(false);
            comentarioService.save(comentario);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
 * This method is used to activate a Comentario.
 * It first finds the Comentario by its id.
 * If the Comentario is not found, it returns a 404 Not Found status.
 * If the Comentario is found, it sets the active status to true if it's not already true.
 * It then saves the updated Comentario to the data
 *
 * @param id The id of the Comentario to be activated. This should be a Long.
 * @return A ResponseEntity that indicates the status of the operation.
 *         If the Comentario is not found, it returns a 404.
 *         If the Comentario is activated successfully, it returns a 204.
 */
@PutMapping("/{id}/reactivate")
public ResponseEntity<Void> activateComment(@PathVariable Long id) {
    Optional<Comentario> comentarioOptional = comentarioService.getCommentById(id);
    if (comentarioOptional.isPresent()) {
        Comentario comentario = comentarioOptional.get();

        comentario.setActivo(true);
        comentarioService.save(comentario);
        return ResponseEntity.noContent().build();
    } else {
        return ResponseEntity.notFound().build();
    }
}

}
