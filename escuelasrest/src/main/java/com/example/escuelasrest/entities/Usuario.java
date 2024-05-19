package com.example.escuelasrest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Entity
@Table (name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 255)
    private String nombre;

    @Setter
    @Column(length = 255)
    private String apellidos;

    @Setter
    @Column(length = 255)
    private String email;

    @Setter
    @Column(length = 255)
    private String password;


    @Setter
    @Column
    private boolean activo;

    @Setter
    @Column
    private long desactivaciones;

    @Setter
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Collection<Colegio> colegios = new ArrayList<>();

    @Setter
    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    @JsonIgnore
    private Rol rol;

    @Setter
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Collection<Comentario> comentarios = new ArrayList<>();

    @Setter
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Collection<Valoracion> valoraciones = new ArrayList<>();



    /**
     * Default constructor for the Usuario class.
     */
    public Usuario() {
    }

    /**
     * Overloaded constructor for the Usuario class.
     * This constructor initializes a new Usuario instance with the provided parameters.
     * @param nombre The name of the Usuario. String.
     * @param apellidos The surname of the Usuario. String.
     * @param email The email of the Usuario. String.
     * @param password The password of the Usuario. String.
     * @param activo The active status of the Usuario. Boolean.
     */
    public Usuario(String nombre, String apellidos, String email, String password, Boolean activo, long desactivaciones) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.activo = activo;
        this.desactivaciones = desactivaciones;
    }



    /**
     * This method adds a Colegio instance to the list of colegios associated with this Usuario instance.
     * It also sets the Usuario of the provided Colegio instance to this Usuario instance.
     * @param colegio The Colegio instance to be added to the list of colegios. Instance of the Colegio class.
     */
    public void addColegio(Colegio colegio) {
        colegios.add(colegio);
        colegio.setUsuario(this);
    }

    /**
     * This method removes a Colegio instance from the list of colegios associated with this Usuario instance.
     * It also sets the Usuario of the provided Colegio instance to null.
     * @param colegio The Colegio instance to be removed from the list of colegios. Instance of the Colegio class.
     */
    public void removeColegio(Colegio colegio) {
        colegios.remove(colegio);
        colegio.setUsuario(null);
    }

    /**
     * This method adds a Comentario instance to the list of comentarios associated with this Usuario instance.
     * It also sets the Usuario of the provided Comentario instance to this Usuario instance.
     * @param comentario The Comentario instance to be added to the list of comentarios. Instance of the Comentario class.
     */
    public void addComentario(Comentario comentario) {
        comentarios.add(comentario);
        comentario.setUsuario(this);
    }

    /**
     * This method removes a Comentario instance from the list of comentarios associated with this Usuario instance.
     * It also sets the Usuario of the provided Comentario instance to null.
     * @param comentario The Comentario instance to be removed from the list of comentarios. Instance of the Comentario class.
     */
    public void removeComentario(Comentario comentario) {
        comentarios.remove(comentario);
        comentario.setUsuario(null);
    }

    /**
     * This method adds a Valoracion instance to the list of valoraciones associated with this Usuario instance.
     * It also sets the Usuario of the provided Valoracion instance to this Usuario instance.
     * @param valoracion The Valoracion instance to be added to the list of valoraciones. Instance of the Valoracion class.
     */
    public void addValoracion(Valoracion valoracion) {
        valoraciones.add(valoracion);
        valoracion.setUsuario(this);
    }

    /**
     * This method removes a Valoracion instance from the list of valoraciones associated with this Usuario instance.
     * It also sets the Usuario of the provided Valoracion instance to null.
     * @param valoracion The Valoracion instance to be removed from the list of valoraciones. Instance of the Valoracion class.
     */
    public void removeValoracion(Valoracion valoracion) {
        valoraciones.remove(valoracion);
        valoracion.setUsuario(null);
    }
}
