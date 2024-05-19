package com.example.escuelasrest.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table (name = "valoracion")
public class Valoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column
    private Integer puntuacion;

    @Setter
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fecha;

    @Setter
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Setter
    @ManyToOne
    @JoinColumn(name = "colegio_id")
    private Colegio colegio;



    /**
     * Default constructor for the Valoracion class.
     */
    public Valoracion() {
    }

    /**
     * Overloaded constructor for the Valoracion class.
     * This constructor initializes a new Valoracion instance with the provided parameters.
     * @param puntuacion The score of the Valoracion. Integer.
     * @param usuario The Usuario associated with the Valoracion. Instance of the Usuario class.
     * @param colegio The Colegio associated with the Valoracion. Instance of the Colegio class.
     */
    public Valoracion(Integer puntuacion,  Usuario usuario, Colegio colegio) {
        this.puntuacion = puntuacion;
        this.usuario = usuario;
        this.colegio = colegio;
    }

    public double getValoracion() {
        return puntuacion;
    }
}