package com.example.escuelasrest.controller;

import com.example.escuelasrest.entities.*;
import com.example.escuelasrest.repositories.ColegioRepository;
import com.example.escuelasrest.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/colegios")
public class ColegioController {

    @Autowired
    private ColegioService colegioService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ActividadService actividadService;

    @Autowired
    private OfertaService ofertaService;

    @Autowired
    private ValoracionService valoracionService;

    @Autowired
    private ColegioRepository ColegioRepository;


    /**
     * This method is used to get all Colegio and their average ratings.
     * It first finds all Colegio.For each Colegio, it finds all Valoracion associated with it.
     * If no Valoracion are found, it sets the average rating to a message indicating that there are no ratings.
     * If Valoracion are found, it calculates the average rating.
     *
     * @return A List of maps. Each map contains a Colegio and its average rating.
     */
    @GetMapping
    public List<Map<String, Object>> getAllSchools(){
        List<Colegio> colegios = colegioService.findAll();
        List<Map<String, Object>> colegiosWithMedia = new ArrayList<>();
        for (Colegio colegio : colegios) {
            List<Valoracion> valoraciones = valoracionService.findByColegioId(colegio.getId());
            String media;
            if (valoraciones.isEmpty()) {
                media = "No hay valoraciones para este colegio";
            } else {
                media = String.format("%.1f", valoraciones.stream()
                        .mapToDouble(Valoracion::getValoracion)
                        .average()
                        .getAsDouble());
            }
            Map<String, Object> colegioMap = new ObjectMapper().convertValue(colegio, Map.class);
            colegioMap.put("mediaValoraciones", media);
            colegiosWithMedia.add(colegioMap);
        }
        return colegiosWithMedia;
    };


    /**
     * This method is used to get a Colegio by its email.
     * It first finds all Colegio with the given email.
     * If no Colegio are found, it returns a 404 Not Found status.
     * If Colegio are found, it checks if any of them are active.
     * If no active Colegio are found, it returns a 400 Bad Request status with an error message.
     * If an active Colegio is found, it calculates the average rating of the Colegio and adds it to the response.
     *
     * @param email The email of the Colegio to be found. This should be a String.
     * @return A ResponseEntity that contains a map with the Colegio and its average rating if it exists and is active.
     *         If no Colegio are found with the given email, it returns a 404 Not Found status.
     *         If no active Colegio are found with the given email, it returns a 400 Bad Request status with an error message.
     */
    @GetMapping("/{email}")
    public ResponseEntity<Map<String, Object>> getColegioByEmail(@PathVariable String email) {
        List<Colegio> colegios = colegioService.findByEmail(email);
        if (colegios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Colegio colegioActivo = null;
        for (Colegio colegio : colegios) {
            if (colegio.isActivo()) {
                colegioActivo = colegio;
                break;
            }
        }
        if (colegioActivo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "No hay colegios activos con el email: " + email));
        }

        List<Valoracion> valoraciones = valoracionService.findByColegioId(colegioActivo.getId());
        String media;
        if (valoraciones.isEmpty()) {
            media = "No hay valoraciones para este colegio";
        } else {
            media = String.format("%.1f", valoraciones.stream()
                    .mapToDouble(Valoracion::getValoracion)
                    .average()
                    .getAsDouble());
        }
        Map<String, Object> colegioMap = new ObjectMapper().convertValue(colegioActivo, Map.class);
        colegioMap.put("mediaValoraciones", media);
        return ResponseEntity.ok(colegioMap);
    }

    /**
     * Method to get all activities for a school
     * @param id of the school
     * @return ResponseEntity with List of activities
     */
    @GetMapping("/actividades/{id}")
    public ResponseEntity<List<Actividad>> getActividadesByColegioId(@PathVariable Long id) {
        List<Oferta> ofertas = ofertaService.findByColegioId(id);
        List<Actividad> actividades = ofertas.stream()
                .map(Oferta::getActividad)
                .collect(Collectors.toList());
        return ResponseEntity.ok(actividades);
    }



    /**
     * This method is used to create a new Colegio and save it to the database.
     * It first finds all Usuario with the given email from the request body.
     * If Usuario are found, it checks if any of them are active.
     * If no active Usuario are found, it returns a 400 Bad Request status with an error message.
     * If an active Usuario is found, it creates a new Colegio and sets its properties from the request body.
     * It then saves the Colegio to the database and returns a 200 OK status with a success message.
     *
     * @param data A map that contains the properties of the Colegio to be created. This should include the email of the associated Usuario.
     * @return A ResponseEntity that contains a success message if the Colegio is created successfully.
     *         If no Usuario are found with the given email, it returns a 404 Not Found status with an error message.
     *         If no active Usuario are found with the given email, it returns a 400 Bad Request status with an error message.
     */
    @PostMapping
    @Transactional
    public ResponseEntity<?> createColegio(@RequestBody Map<String, Object> data) {
        List<Usuario> usuarios = usuarioService.findByEmail(data.get("email").toString());
        if (usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado con email: " + data.get("email").toString());
        }

        Usuario usuarioActivo = null;
        for (Usuario usuario : usuarios) {
            if (usuario.isActivo()) {
                usuarioActivo = usuario;
                break;
            }
        }
        if (usuarioActivo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay usuarios activos con el email: " + data.get("email").toString());
        }

        System.out.println(data);
        Colegio school = new Colegio();
        school.setNombre((String) data.get("nombre"));
        school.setDireccion((String) data.get("direccion"));
        school.setTelefono((String) data.get("telefono").toString());
        school.setEmail((String) data.get("email"));
        school.setUsuario(usuarioActivo);
        System.out.println("Usuario asignado al colegio: " + school.getUsuario());
        school.setConcierto((Boolean) data.get("concierto"));
        school.setDenominacion((String) data.get("denominacion"));
        school.setLocalidad((String) data.get("localidad"));
        school.setProvincia((String) data.get("provincia"));
        school.setWeb((String) data.get("web"));
        school.setNaturaleza((String) data.get("naturaleza"));
        school.setComedor((Boolean) data.get("comedor"));
        school.setLocalizacion((String) data.get("localizacion"));
        school.setCodigo((String) data.get("codigo"));
        school.setActivo(true);
        school.setDesactivaciones(0L);
        Colegio savedColegio = colegioService.save(school);
        return ResponseEntity.ok("Colegio creado con ID: " + savedColegio.getId());
    }


    /**
     * Method to get all the images of a school
     * @param id of the school
     * @return ResponseEntity with List of images
     * or ResponseEntity with bad request if the school is not found
     */
    @GetMapping("/id/{id}/imagenes")
    public ResponseEntity<List<Imagen>> getImagesBySchoolId (@PathVariable Long id) {
        Optional<Colegio> colegioOptional = ColegioRepository.findById(id);
        if (colegioOptional.isPresent()) {
            Colegio colegio = colegioOptional.get();
            List<Imagen> imagenes = colegio.getImagenes();
            return ResponseEntity.ok(imagenes);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }



    /**
     * This method is used to reactivate a Colegio and its associated Usuario.
     * It first finds the Colegio by its id.
     * If the Colegio is not found, it throws a RuntimeException.
     * If the Colegio is found, it gets the associated Usuario.
     * It then checks if there is any active Colegio with the same email.
     * If there is an active Colegio with the same email, it returns a 409 Conflict status with an error message.
     * If there is no active Colegio with the same email, it sets the active status of the Usuario and the Colegio to true.
     * It then saves the updated Usuario and Colegio to the database.
     * The method returns a 200 OK status with a success message.
     *
     * @param id The id of the Colegio to be reactivated. This should be a Long.
     * @return A ResponseEntity that contains a success message if the Colegio and its associated Usuario are reactivated successfully.
     *         If the Colegio is not found, it throws a RuntimeException.
     *         If there is an active Colegio with the same email, it returns a 409 Conflict status with an error message.
     */
    @PutMapping("/reactivate/{id}")
    public ResponseEntity<String> reactiveUsuario(@PathVariable Long id) {
        // Find the Colegio by id
        Optional<Colegio> colegioOptional = colegioService.findById(id);
        if (!colegioOptional.isPresent()) {
            throw new RuntimeException("Colegio no encontrado con ID: " + id);
        }

        Colegio colegio = colegioOptional.get();

        // Get the associated Usuario
        Usuario usuario = colegio.getUsuario();

        // Check if there is any active Colegio with the same email
        List<Colegio> colegiosActivos = colegioService.findByEmailAndActivo(colegio.getEmail(), true);
        if (!colegiosActivos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un colegio activo con el correo electrónico: " + colegio.getEmail());
        }

        // Reactivate the Usuario and Colegio
        usuario.setActivo(true);
        colegio.setActivo(true);

        // Save the updated entities
        usuarioService.save(usuario);
        colegioService.save(colegio);

        return ResponseEntity.ok("Usuario y colegio asociado reactivados exitosamente");
    }


/**
 * This method is used to deactivate a Colegio and its associated Usuario.
 * It first finds all Usuario with the given email.
 * If no Usuarioare found, it throws a RuntimeException.
 * If Usuario are found, it increments the desactivaciones property of each Usuario and sets the active status to false if it's not already false.
 * It then finds all Colegio with the given email.
 * For each Colegio, it increments the desactivaciones property and sets the active status to false if it's not already false.
 * The method returns a 200 OK status with a success message.
 *
 * @param email The email of the Usuario and Colegio to be deactivated. This should be a String.
 * @return A ResponseEntity that contains a success message if the Usuario and Colegio are deactivated successfully.
 *         If no Usuario are found with the given email, it throws a RuntimeException.
 */
@PutMapping("/deactivate/{email}")
public ResponseEntity<String> desactiveColegio(@PathVariable String email) {
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
    });

    List<Colegio> colegios = colegioService.findByEmail(email);
    colegios.forEach(colegio -> {
        colegio.setDesactivaciones(colegio.getDesactivaciones() + 1);
        if (colegio.isActivo()) {
            colegio.setActivo(false);
        }
        colegioService.save(colegio);
    });

    return ResponseEntity.ok("Usuarios y colegios vinculados desactivados y desactivaciones incrementadas");
}


    /**
     * This method is used to create a new Actividad and an associated Oferta.
     * It first finds all Colegio with the given email.
     * If no Colegio are found, it throws a RuntimeException.
     * If Colegio are found, it checks if any of them are active.
     * If no active Colegio are found, it throws a RuntimeException.
     * If an active Colegio is found, it creates a new Actividad and sets its properties from the request body.
     * It then creates a new Oferta, sets the Colegio and the Actividad to it, and saves it to the database.
     * The method returns a 200 OK status with the new Actividad.
     *
     * @param colegioEmail The email of the Colegio to be associated with the Oferta. This should be a String.
     * @param data A map that contains the properties of the Actividad to be created. This should include the name and type of the Actividad.
     * @return A ResponseEntity that contains the new Actividad if it is created successfully.
     *         If no Colegio are found with the given email, it throws a RuntimeException.
     *         If no active Colegio are found with the given email, it throws a RuntimeException.
     */
    @PostMapping("/{colegioEmail}/actividades")
    public ResponseEntity<Actividad> createActividadAndOferta(@PathVariable String colegioEmail, @RequestBody Map<String, Object> data) {
        List<Colegio> colegios = colegioService.findByEmail(colegioEmail);
        if (colegios.isEmpty()) {
            throw new RuntimeException("Colegio no encontrado con email: " + colegioEmail);
        }

        Colegio colegioActivo = null;
        for (Colegio colegio : colegios) {
            if (colegio.isActivo()) {
                colegioActivo = colegio;
                break;
            }
        }

        if (colegioActivo == null) {
            throw new RuntimeException("No hay colegios activos con el email: " + colegioEmail);
        }

        Actividad actividad = new Actividad();
        actividad.setNombre((String) data.get("name"));
        actividad.setTipo((String) data.get("tipe"));
        actividad.setActivo(true);
        Actividad newActividad = actividadService.createActividad(actividad);
        Oferta oferta = new Oferta();
        oferta.setColegio(colegioActivo);
        oferta.setActividad(newActividad);
        ofertaService.createOferta(oferta);
        return ResponseEntity.ok(newActividad);
    }


    /**
     * This method is used to create a new Oferta and associate it with a Colegio and an Actividad.
     * It first finds all Colegio with the given email.
     * If no Colegio are found, it throws a RuntimeException.
     * If Colegio are found, it checks if any of them are active.
     * If no active Colegio are found, it throws a RuntimeException.
     * If an active Colegio is found, it finds the Actividad by its id.
     * If the Actividad is not found, it throws a RuntimeException.
     * If the Actividad is found, it creates a new Oferta, sets the Colegio and the Actividad to it, and saves it to the database.
     * The method returns a 200 OK status with the new Oferta.
     *
     * @param colegioEmail The email of the Colegio to be associated with the Oferta. This should be a String.
     * @param actividadId The id of the Actividad to be associated with the Oferta. This should be a Long.
     * @return A ResponseEntity that contains the new Oferta if it is created successfully.
     *         If no Colegio are found with the given email, it throws a RuntimeException.
     *         If no active Colegio are found with the given email, it throws a RuntimeException.
     *         If the Actividad is not found, it throws a RuntimeException.
     */
    @PostMapping("/{colegioEmail}/actividades/{actividadId}")
    public ResponseEntity<Oferta> createOferta(@PathVariable String colegioEmail, @PathVariable Long actividadId) {
        List<Colegio> colegios = colegioService.findByEmail(colegioEmail);
        if (colegios.isEmpty()) {
            throw new RuntimeException("Colegio no encontrado con email: " + colegioEmail);
        }

        Colegio colegioActivo = null;
        for (Colegio colegio : colegios) {
            if (colegio.isActivo()) {
                colegioActivo = colegio;
                break;
            }
        }

        if (colegioActivo == null) {
            throw new RuntimeException("No hay colegios activos con el email: " + colegioEmail);
        }

        Optional<Actividad> optionalActividad = actividadService.findById(actividadId);
        if (!optionalActividad.isPresent()) {
            throw new RuntimeException("Actividad no encontrada con ID: " + actividadId);
        }
        Actividad actividad = optionalActividad.get();
        Oferta oferta = new Oferta();
        oferta.setColegio(colegioActivo);
        oferta.setActividad(actividad);
        ofertaService.createOferta(oferta);
        return ResponseEntity.ok(oferta);
    }


    /**
     * This method is used to update the description of an active Colegio.
     * It first finds all Colegio with the given email.
     * If Colegio are found, it checks if any of them are active.
     * If an active Colegio is found, it sets the description property from the request body and saves the updated Colegio to the database.
     * @param email The email of the Colegio to be updated. This should be a String.
     * @param data A map that contains the new description of the Colegio.
     * @return A message if the description of the Colegio is updated successfully.
     *         or a bad request if the Colegio is not found.
     */
    @PutMapping("/descripcion/{email}")
    public ResponseEntity<Map<String, String>> updateDescripcion(@PathVariable String email, @RequestBody Map<String, Object> data) {
        List<Colegio> colegios = colegioService.findByEmail(email);
        if (colegios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Colegio no encontrado con email: " + email));
        }

        Colegio colegioActivo = null;
        for (Colegio colegio : colegios) {
            if (colegio.isActivo()) {
                colegioActivo = colegio;
                break;
            }
        }

        if (colegioActivo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "No hay colegios activos con el email: " + email));
        }
        
        colegioActivo.setDescripcion((String) data.get("description"));
        colegioService.save(colegioActivo);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Descripción actualizada con éxito");
        return ResponseEntity.ok(response);
    }


    /**
     * This method is used to delete a Colegio and its associated Usuario.
     * It first finds the Colegio by its id.
     * If the Colegio is found, it gets the associated Usuario.
     * It then deletes the Usuario and the Colegio from the database.
     *
     * @param id The id of the Colegio to be deleted. This should be a Long.
     * @return A ResponseEntity that indicates the status of the operation.
     *         If the Colegio is not found, it returns a 404 Not Found status.
     *         If the Colegio is found and deleted successfully, it returns a 200 OK status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColegio(@PathVariable Long id) {
        Optional<Colegio> colegioOptional = colegioService.findById(id);
        if (!colegioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Colegio colegio = colegioOptional.get();
        Usuario usuario = colegio.getUsuario();
        usuarioService.delete(usuario);
        colegioService.delete(colegio);
        return ResponseEntity.ok().build();
    }
}
