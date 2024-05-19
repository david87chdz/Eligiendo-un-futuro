package com.example.escuelasrest.controller;

import com.example.escuelasrest.entities.Oferta;
import com.example.escuelasrest.services.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/ofertas")
@RestController
public class OfertaController {

    @Autowired
    private OfertaService ofertaService;

    /**
     * This method is a DELETE request mapped to "/api/ofertas/{colegioId}/{actividadId}/delete".
     * It deletes an offer associated with a specific school and activity, both identified by their IDs.
     *
     * @param colegioId The ID of the school associated with the offer to be deleted.
     * @param actividadId The ID of the activity associated with the offer.
     * @return A ResponseEntity containing a success message if the operation is successful.
     *         The status code is 200 (OK) if the operation is successful.
     *         or bad request if the offer is not found.
     */
    @DeleteMapping("/{colegioId}/{actividadId}/delete")
    public ResponseEntity<String> deleteOferta(@PathVariable Long colegioId, @PathVariable Long actividadId) {
        Optional<Oferta> optionalOferta = ofertaService.findByColegioIdAndActividadId(colegioId, actividadId);
        if (!optionalOferta.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la oferta con ID de colegio: " + colegioId + " y ID de actividad: " + actividadId);
        }
        Oferta oferta = optionalOferta.get();
        ofertaService.delete(oferta);
        return ResponseEntity.ok("Oferta eliminada con éxito");
    }


    /**
 * This method is a GET request mapped to "/api/ofertas".
 * It retrieves all Oferta from the service.
 * It returns a ResponseEntity containing a List of all Oferta.
 *
 * @return A ResponseEntity containing a List of all Oferta.
 *         The status code is 200 (OK) if the operation is successful.
 */
@GetMapping
public ResponseEntity<List<Oferta>> getAllOfertas() {
    List<Oferta> ofertas = ofertaService.getAllOfertas();
    return ResponseEntity.ok(ofertas);
}

}
