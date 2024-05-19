package com.example.escuelasrest.controller;

import com.example.escuelasrest.entities.Colegio;
import com.example.escuelasrest.entities.Usuario;
import com.example.escuelasrest.entities.Valoracion;
import com.example.escuelasrest.services.ColegioService;
import com.example.escuelasrest.services.UsuarioService;
import com.example.escuelasrest.services.ValoracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/valoraciones")
public class ValoracionController {

    @Autowired
    private ValoracionService valoracionService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ColegioService colegioService;

    /**
     * This method is a GET request mapped to the root of the "/api/valoraciones" endpoint.
     * It retrieves all Valoracion entities from the system.
     *
     * @return A ResponseEntity containing a List of Valoracion entities.
     *         If no Valoracion entities are found, this will be an empty list.
     */
    @GetMapping
    public ResponseEntity<List<Valoracion>> getAllRatings() {
        List<Valoracion> valoraciones = valoracionService.getAllRatings();
        return ResponseEntity.ok(valoraciones);
    }

    /**
     * This method is a GET request mapped to "/api/valoraciones/{id}".
     * It retrieves a Valoracion entity identified by its ID.
     *
     * @param id The ID of the Valoracion to be retrieved. This should be a Long.
     * @return A ResponseEntity containing the Valoracion entity if found.
     *         If the Valoracion associated with the provided ID cannot be found, the status code is 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Valoracion> getRatingById(@PathVariable Long id) {
        Optional<Valoracion> valoracion = valoracionService.getRatingById(id);
        return valoracion.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * This method is a GET request mapped to "/api/valoraciones/colegio/{id}".
     * It retrieves all Valoracion entities associated with a specific Colegio, identified by its ID.
     *
     * @param id The ID of the Colegio for which the Valoracion entities are to be retrieved. This should be a Long.
     * @return A ResponseEntity containing a List of Valoracion entities associated with the specified Colegio.
     *         If no Valoracion entities are found, this will be an empty list.
     */
    @GetMapping("/colegio/{id}")
    public ResponseEntity<List<Valoracion>> getRatingsBySchoolId(@PathVariable Long id) {
        List<Valoracion> valoraciones = valoracionService.getRatingsBySchoolId(id);
        return ResponseEntity.ok(valoraciones);
    }

    /**
     * This method is a POST request mapped to the root of the "/api/valoraciones" endpoint.
     * It creates a new Valoracion entity in the system.
     *
     * @param data A Map containing the key-value pairs representing the fields of the Valoracion to be created.
     *             The keys can be "usuarioId", "colegioId", and "puntuacion".
     *             "usuarioId" and "colegioId" should be Integers representing the IDs of the Usuario and Colegio entities associated with the Valoracion.
     *             "puntuacion" should be an Integer representing the rating given by the Usuario to the Colegio.
     * @return A ResponseEntity containing the created Valoracion entity.
     *         The status code is 201 (Created) if the operation is successful.
     * @throws RuntimeException If the Usuario or Colegio associated with the provided IDs cannot be found.
     */
    @PostMapping
    public ResponseEntity<Valoracion> createRating(@RequestBody Map<String, Object> data) {
        Long usuarioId = Long.valueOf((Integer) data.get("usuarioId"));
        Usuario usuario = usuarioService.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));
        Long colegioId = Long.valueOf((Integer) data.get("colegioId"));
        Colegio colegio = colegioService.findById(colegioId)
                .orElseThrow(() -> new RuntimeException("Colegio no encontrado con ID: " + colegioId));
        Valoracion valoracion = new Valoracion();
        valoracion.setPuntuacion((Integer) data.get("puntuacion"));
        valoracion.setUsuario(usuario);
        valoracion.setColegio(colegio);
        Valoracion newValoracion = valoracionService.createRating(valoracion);
        return ResponseEntity.created(URI.create("/api/valoraciones/" + newValoracion.getId())).body(newValoracion);
    }


    /**
     * This method is a PUT request mapped to "/api/valoraciones/{id}".
     * It updates a Valoracion entity identified by its ID with the provided Valoracion data.
     *
     * @param id The ID of the Valoracion to be updated. This should be a Long.
     * @param valoracion A Valoracion object containing the updated data for the Valoracion.
     *                   This should be provided in the request body.
     * @return A ResponseEntity with a status code of 204 (No Content) if the operation is successful.
     * @throws RuntimeException If the Valoracion associated with the provided ID cannot be found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRating(@PathVariable Long id, @RequestBody Valoracion valoracion) {
        valoracionService.updateRating(id, valoracion);
        return ResponseEntity.noContent().build();
    }

    /**
     * This method is a DELETE request mapped to "/api/valoraciones/{id}".
     * It deletes a Valoracion entity identified by its ID from the system.
     *
     * @param id The ID of the Valoracion to be deleted. This should be a Long.
     * @return A ResponseEntity with a status code of 204 (No Content) if the operation is successful.
     * @throws RuntimeException If the Valoracion associated with the provided ID cannot be found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
        valoracionService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Valoracion>> getValoracionesByUsuarioId(@PathVariable Long id) {
        List<Valoracion> valoraciones = valoracionService.getValoracionesByUsuarioId(id);
        if (valoraciones.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(valoraciones);
    }

}

