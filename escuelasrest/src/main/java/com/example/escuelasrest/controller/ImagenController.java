package com.example.escuelasrest.controller;

import com.example.escuelasrest.entities.Colegio;
import com.example.escuelasrest.entities.Imagen;
import com.example.escuelasrest.repositories.ColegioRepository;
import com.example.escuelasrest.repositories.ImagenRepository;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/imagenes")
public class ImagenController {

    @Autowired
    private ImagenRepository imagenRepository;

    @Autowired
    private ColegioRepository colegioRepository;


    /**
     * This method is a GET request.
     * It retrieves all Imagen from the database.
     *
     * @return A ResponseEntity containing a list of all Imagen.
     * The status code is 200 (OK) if the operation is successful.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Imagen>> getAllImages() {
        List<Imagen> imagenes = imagenRepository.findAll();
        return ResponseEntity.ok(imagenes);
    }


    /**
     * This method is a GET request mapped .
     * It retrieves all images associated with a specific school identified by its ID.
     *
     * @param id The ID of the school for which images are to be retrieved.
     * @return A ResponseEntity containing a list of Base64 encoded strings representing the images associated with the school.
     * The status code is 200 (OK) if the operation is successful.
     * If the school associated with the provided ID cannot be found, the status code is 400 (Bad Request) and an empty list is returned.
     * @throws RuntimeException If there is an error converting the Blob to a String.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<List<String>> getImgBySchoolId(@PathVariable Long id) {
        Optional<Colegio> colegioOptional = colegioRepository.findById(id);
        if (colegioOptional.isPresent()) {
            Colegio colegio = colegioOptional.get();
            List<Imagen> imagenes = colegio.getImagenes();
            List<String> urls = imagenes.stream()
                    .peek(imagen -> System.out.println("Imagen ID: " + imagen.getId() + ", Estado activo: " + imagen.isActivo())) // Imprime el estado activo de cada imagen
                    .filter(imagen -> imagen.isActivo()) // Solo incluye las imágenes que están activas
                    .map(imagen -> {
                        try {
                            byte[] bytes = imagen.getUrl().getBytes(1, (int) imagen.getUrl().length());
                            return Base64.getEncoder().encodeToString(bytes);
                        } catch (SQLException e) {
                            throw new RuntimeException("Error al convertir Blob a String", e);
                        }
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(urls);
        } else {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    /**
     * This method is a GET request.
     * It retrieves all active images associated with a specific school identified by its email.
     * It first finds all Colegio with the given email.
     * If Colegio are found, it checks if any of them are active.
     * If an active Colegio is found, it retrieves all associated Imagen.
     * It then converts the URL of each Imagen to a String and collects them into a list.
     *
     * @param email The email of the Colegio for which images are to be retrieved. This should be a String.
     * @return A ResponseEntity that contains a list of image URLs if the operation is successful.
     * Or bad request if no Colegio are found with the given email.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<String> getImgBySchoolEmail(@PathVariable String email) {
        List<Colegio> colegios = colegioRepository.findByEmail(email);
        if (colegios.isEmpty()) {
            return ResponseEntity.badRequest().body("No se encontró un colegio con el email proporcionado");
        }

        Colegio colegioActivo = null;
        for (Colegio colegio : colegios) {
            if (colegio.isActivo()) {
                colegioActivo = colegio;
                break;
            }
        }

        if (colegioActivo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay colegios activos con el email: " + email);
        }

        List<Imagen> imagenes = colegioActivo.getImagenes();
        List<String> urls = imagenes.stream()
                .map(Imagen::getUrlAsString)
                .collect(Collectors.toList());
        return ResponseEntity.ok(urls.toString());
    }


    /**
     * This method is a POST request                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                .
     * It uploads an image file associated with a specific school identified by its email.
     * It first finds all Colegio with the given email.
     * If Colegio are found, it checks if any of them are active.
     * - Defines the image file path
     * - Saves the image file in the server folder
     * - Converts the MultipartFile to a Blob
     * - Creates a new Imagen and sets the Colegio
     * - Saves the Imagen to the database
     *
     * @param image The image file to be uploaded. This should be a MultipartFile.
     * @param email The email of the Colegio for which the image is to be uploaded. This should be a String.
     * @return A ResponseEntity that contains a success message if the image is uploaded successfully.
     * Or a bad request if no Colegio are found with the given email.
     * @throws IOException If there is an error writing the image file to the server folder or converting the MultipartFile to a Blob.
     */
    @PostMapping("/uploadFile/{email}")
    public ResponseEntity<String> uploadImageFile(@RequestParam("images") MultipartFile image, @PathVariable String email) {
        List<Colegio> colegios = colegioRepository.findByEmail(email);
        if (colegios.isEmpty()) {
            return ResponseEntity.badRequest().body("No se encontró un colegio con el email proporcionado");
        }

        Colegio colegioActivo = null;
        for (Colegio colegio : colegios) {
            if (colegio.isActivo()) {
                colegioActivo = colegio;
                break;
            }
        }

        if (colegioActivo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay colegios activos con el email: " + email);
        }

        try {
            // Define la ruta del archivo de la imagen
            String imageUrl = "src/main/resources/static/assets/" + colegioActivo.getCodigo() + "/" + image.getOriginalFilename();

            // Guarda la imagen en la carpeta del servidor
            Path path = Paths.get(imageUrl);
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            Files.write(path, image.getBytes());

            // convierte MultipartFile a Blob
            Blob blob = BlobProxy.generateProxy(image.getBytes());

            // crea una nueva instancia de Imagen y establece el colegio
            Imagen imagen = new Imagen(blob, imageUrl, colegioActivo, true);

            imagenRepository.save(imagen);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al procesar la imagen");
        }

        return ResponseEntity.ok("Imágenes subidas con éxito");
    }

    /**
     * This method is a PUT request.
     * It reactivates an Imagen identified by its id.
     * It first finds the Imagen by its id.
     * If the Imagen is found, it sets the active status to true if it's not already true.
     * It then saves the updated Imagen to the database.
     *
     * @param id The id of the Imagen to be reactivated. This should be a Long.
     * @return A ResponseEntity that contains a success message if the Imagen is reactivated successfully.
     * or a bad request if the Imagen is not found.
     */
    @PutMapping("/reactivate/{id}")
    public ResponseEntity<Map<String, String>> activateImage(@PathVariable Long id) {
        Optional<Imagen> imagenOptional = imagenRepository.findById(id);
        if (imagenOptional.isPresent()) {
            Imagen imagen = imagenOptional.get();
            imagen.setActivo(true);
            imagenRepository.save(imagen);
            return ResponseEntity.ok(Collections.singletonMap("message", "Imagen activada con éxito"));
        } else {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "No se encontró una imagen con el ID proporcionado"));
        }
    }

    /**
     * This method is a PUT request.
     * It deactivates an Imagen identified by its id.
     * It first finds the Imagen by its id.
     * If the Imagen is found, it sets the active status to false if it's not already false.
     * It then saves the updated Imagen to the database.
     *
     * @param id The id of the Imagen to be deactivated. This should be a Long.
     * @return A ResponseEntity that contains a success message if the Imagen is deactivated successfully.
     * or a bad request if the Imagen is not found.
     */
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Map<String, String>> deactivateImage(@PathVariable Long id) {
        Optional<Imagen> imagenOptional = imagenRepository.findById(id);
        if (imagenOptional.isPresent()) {
            Imagen imagen = imagenOptional.get();
            imagen.setActivo(false);
            imagenRepository.save(imagen);
            return ResponseEntity.ok(Collections.singletonMap("message", "Imagen desactivada con éxito"));
        } else {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "No se encontró una imagen con el ID proporcionado"));
        }
    }

    /**
     * This method is a DELETE request.
     * It deletes an Imagen identified by its id.
     * It first finds the Imagen by its id.
     * If the Imagen is found, it deletes the Imagen from the database.
     *
     * @param id The id of the Imagen to be deleted. This should be a Long.
     * @return A ResponseEntity that contains a success message if the Imagen is deleted successfully.
     * or a bad request if the Imagen is not found.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id) {
        Optional<Imagen> imagenOptional = imagenRepository.findById(id);
        if (imagenOptional.isPresent()) {
            imagenRepository.deleteById(id);
            return ResponseEntity.ok("Imagen borrada con éxito");
        } else {
            return ResponseEntity.badRequest().body("No se encontró una imagen con el ID proporcionado");
        }
    }
}
 