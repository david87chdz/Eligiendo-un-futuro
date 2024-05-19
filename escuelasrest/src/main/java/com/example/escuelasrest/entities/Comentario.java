package com.example.escuelasrest.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table (name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 255)
    private String contenido;

    @Setter
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime fecha;

    @Setter
    @Column
    private Boolean activo;

    @Setter
    @ManyToOne
    private Usuario usuario;

    @Setter
    @ManyToOne
    private Colegio colegio;

   

    /**
     * Default constructor for the Comentario class.
     */
    public Comentario() {
    }

    /**
     * Overloaded constructor for the Comentario class.
     * This constructor initializes a new Comentario instance with the provided parameters.
     * @param contenido The content of the Comentario. String.
     * @param fecha The date of the Comentario. Instance of LocalDateTime.
     * @param usuario The user associated with the Comentario. Instance of the Usuario class.
     * @param colegio The school associated with the Comentario. Instance of the Colegio class.
     * @param activo The status of the Comentario. Boolean.
     */
    public Comentario(String contenido, LocalDateTime fecha, Usuario usuario, Colegio colegio, Boolean activo) {
        this.contenido = contenido;
        this.fecha = fecha;
        this.usuario = usuario;
        this.colegio = colegio;
        this.activo= activo;
    }

    /**
     * Overloaded constructor for the Comentario class.
     * This constructor initializes a new Comentario instance with the provided parameters, excluding the date.
     * @param contenido The content of the Comentario. String.
     * @param usuario The user associated with the Comentario. Instance of the Usuario class.
     * @param colegio The school associated with the Comentario. Instance of the Colegio class.
     * @param activo The status of the Comentario. Boolean.
     */
    public Comentario(String contenido, Usuario usuario, Colegio colegio, Boolean activo) {
        this.contenido = contenido;
        this.usuario = usuario;
        this.colegio = colegio;
        this.activo = activo;
    }


    public void setActivo(boolean b) {
        this.activo = b;
    }
}