package com.example.escuelasrest.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table (name = "respuesta")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 255)
    private String contenido;

    @Setter
    @Column(columnDefinition = "DATE")
    private LocalDateTime fecha;



    @Setter
    @OneToOne(cascade = CascadeType.REFRESH)
    private Comentario comentario;

    @Setter
    @ManyToOne
    private Colegio colegio;



    /**
     * Default constructor for the Respuesta class.
     */
    public Respuesta() {
    }

    /**
     * Overloaded constructor for the Respuesta class.
     * This constructor initializes a new Respuesta instance with the provided parameters.
     * @param contenido The content of the Respuesta. String.
     * @param fecha The date of the Respuesta. Instance of LocalDateTime.
     * @param activo The status of the Respuesta. Boolean.
     * @param comentario The comment associated with the Respuesta. Instance of the Comentario class.
     * @param colegio The school associated with the Respuesta. Instance of the Colegio class.
     */
    public Respuesta(String contenido, LocalDateTime fecha, Boolean activo, Comentario comentario, Colegio colegio) {
        this.contenido = contenido;
        this.fecha = fecha;
        this.comentario = comentario;
        this.colegio = colegio;
    }

    /**
     * Overloaded constructor for the Respuesta class.
     * This constructor initializes a new Respuesta instance with the provided parameters.
     * @param contenido The content of the Respuesta. String.
     * @param comentario The comment associated with the Respuesta. Instance of the Comentario class.
     * @param activo The status of the Respuesta. Boolean.
     * @param colegio The school associated with the Respuesta. Instance of the Colegio class.
     */
    public Respuesta(String contenido, Comentario comentario, Boolean activo, Colegio colegio) {
        this.contenido = contenido;
        this.comentario = comentario;
        this.colegio = colegio;
    }

}