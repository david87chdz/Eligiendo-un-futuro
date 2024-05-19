package com.example.escuelasrest.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table (name = "oferta")
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Setter
    @ManyToOne
    //@JsonBackReference
    @JoinColumn(name = "actividad_id", nullable = false)
    private Actividad actividad;

    @Setter
    @ManyToOne
    //@JsonBackReference
    @JoinColumn(name = "colegio_id", nullable = false)
    private Colegio colegio;



    /**
     * Default constructor for the Oferta class.
     */
    public Oferta() {
    }

    /**
     * Overloaded constructor for the Oferta class.
     * This constructor initializes a new Oferta instance with the provided parameters.
     * @param actividad The activity associated with the Oferta. Instance of the Actividad class.
     * @param colegio The school associated with the Oferta. Instance of the Colegio class.
     */
    public Oferta(Actividad actividad, Colegio colegio) {
        this.actividad = actividad;
        this.colegio = colegio;
    }
    public Long getId() {
        return id;
    }



    public Actividad getActividad() {
        return actividad;
    }


    public Colegio getColegio() {
        return colegio;
    }


}
