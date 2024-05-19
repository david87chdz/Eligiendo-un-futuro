package com.example.escuelasrest.controller;

import com.example.escuelasrest.entities.Colegio;
import com.example.escuelasrest.entities.Comentario;
import com.example.escuelasrest.entities.Respuesta;
import com.example.escuelasrest.services.ColegioService;
import com.example.escuelasrest.services.ComentarioService;
import com.example.escuelasrest.services.RespuestaService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Getter
@RestController
@RequestMapping("/api/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestasService;

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private ColegioService colegioService;


    /**
     * This method is a GET request.
     * It retrieves all answers in the system.
     * @return A ResponseEntity containing a list of all answers.
     *         The status code is 200 (OK) if the operation is successful.
     */
    @GetMapping
    public ResponseEntity<List<Respuesta>> getAnswers() {
        List<Respuesta> respuestas = respuestasService.getAllAnswers();
        return ResponseEntity.ok(respuestas);
    }


    /**
     * This method is a GET request.
     * It retrieves an answer identified by its ID.
     * @param id The ID of the answer to be retrieved. This should be a Long.
     * @return A ResponseEntity containing the answer associated with the provided ID.
     *         The status code is 200 (OK) if the answer is found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Respuesta> getAnswerById(@PathVariable Long id) {
        Optional<Respuesta> respuesta = respuestasService.getAnswerById(id);
        return respuesta.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    /**
     * This method is a POST request.
     * It creates a new answer associated with a specific school and comment, both by their IDs.
     *
     * @param data A Map containing the following key-value pairs:
     *             - "colegioId": The ID of the school associated with the answer to be created.
     *             - "comentarioId": The ID of the comment associated with the answer to be created.
     *             - "contenido": The content of the answer to be created.
     * @return A ResponseEntity containing the newly created answer.
     *         The status code is 201 (Created) if the operation is successful.
     */
    @PostMapping
    public ResponseEntity<Respuesta> createAnswer(@RequestBody Map<String, Object> data) {
        Long colegioId = Long.valueOf((Integer) data.get("colegioId"));
        Colegio colegio = colegioService.findById(colegioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + colegioId));
        Long comentarioId = Long.valueOf((Integer) data.get("comentarioId"));
        Comentario comentario = comentarioService.getCommentById(comentarioId)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado con ID: " + comentarioId));
        Respuesta respuesta = new Respuesta();
        respuesta.setContenido((String) data.get("contenido"));
        respuesta.setColegio(colegio);
        respuesta.setComentario(comentario);
        Respuesta newRespuesta = respuestasService.createAnswer(respuesta);
        return ResponseEntity.created(URI.create("/api/respuestas/" + newRespuesta.getId())).body(newRespuesta);
    }





    /**
     * This method is a DELETE request mapped to "/api/respuestas/{id}".
     * It deletes an answer identified by its ID.
     * @param id The ID of the answer to be deleted. This should be a Long.
     * @return A ResponseEntity indicating the result of the operation.
     *         The status code is 204 if the operation is successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta> deleteRespuesta(@PathVariable Long id) {
        respuestasService.deleteAnswer(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * This method is a GET request mapped to "/api/respuestas/comentario/{id}".
     * It retrieves all answers associated with a specific comment, identified by its ID.
     * @param id The ID of the comment for which the answers are to be retrieved. This should be a Long.
     * @return A list of answers.
     *         The status code is 200
     */
    @GetMapping("/comentario/{id}")
    public ResponseEntity<List<Respuesta>> getRespuestasByComentarioId(@PathVariable Long id) {
        List<Respuesta> respuestas = respuestasService.getAnswersByCommentId(id);
        return ResponseEntity.ok(respuestas);
    }




    /**
     * This method is a PUT request mapped to "/api/respuestas/{id}".
     * It updates an existing answer identified by its ID.
     *
     * @param id The ID of the answer to be updated. This should be a Long.
     * @param data A Map containing the key-value pairs representing the fields to be updated.
     * @return The status code is 200 if the operation is successful.
     *         or bad request if the Respuesta not found .
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRespuesta(@PathVariable Long id, @RequestBody Map<String, Object> data) {
        Optional<Respuesta> respuestaOptional = respuestasService.getAnswerById(id);
        if (!respuestaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Respuesta existingRespuesta = respuestaOptional.get();
        existingRespuesta.setContenido((String) data.get("contenido"));
        respuestasService.updateAnswer(existingRespuesta);
        return ResponseEntity.ok().build();
    }
}
