package com.example.escuelasrest.controller;

import com.example.escuelasrest.entities.Actividad;
import com.example.escuelasrest.services.ActividadService;
import com.example.escuelasrest.services.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/actividades")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    @Autowired
    private OfertaService ofertaService;

    /**
     * Get all activities
     * @return List of activities
     */
    @GetMapping
    public ResponseEntity<List<Actividad>> getActivities() {
        List<Actividad> actividades = actividadService.getAllActivities();
        return ResponseEntity.ok(actividades);
    }

}
