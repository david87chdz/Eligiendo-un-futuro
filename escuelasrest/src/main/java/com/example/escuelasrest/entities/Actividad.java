package com.example.escuelasrest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table (name = "actividad")
public class Actividad {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 255)
    private String nombre;


    @Setter
    @Column(length = 20)
    private String tipo;

    @Setter
    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean activo;

    @Setter
    @JsonIgnore
    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Oferta> ofertas = new ArrayList<>();


    /**
     * The default constructor of the Actividad class.
     */
    public Actividad() {
    }

    /**
     * A constructor for the Actividad class that initializes the name, type, and status of the Actividad.
     * @param nombre The name of the Actividad. String.
     * @param tipo The type of the Actividad. String.
     * @param activo The status of the Actividad. Boolean.
     */
    public Actividad(String nombre, String tipo, Boolean activo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.activo= activo;
    }



    /**
     * This method adds an Oferta to the list of ofertas associated with the Actividad.
     * It also sets the Actividad of the Oferta to this Actividad.
     * @param oferta The Oferta to be added. Instance of the Oferta class.
     */
    public void addOferta(Oferta oferta) {
        ofertas.add(oferta);
        oferta.setActividad(this);
    }

    /**
     * This method removes an Oferta from the list of ofertas associated with the Actividad.
     * It also sets the Actividad of the Oferta to null.
     * @param oferta The Oferta to be removed. Instance of the Oferta class.
     */
    public void removeOferta(Oferta oferta) {
        ofertas.remove(oferta);
        oferta.setActividad(null);
    }
}