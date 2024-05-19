package com.example.escuelasrest.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "rol")
public class Rol {

  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 20)
    private String tipo;

    @Setter
    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Usuario> usuarios = new ArrayList<>();


    /**
     * Default constructor for the Rol class.
     */
    public Rol() {
    }

    /**
     * Overloaded constructor for the Rol class.
     * This constructor initializes a new Rol instance with the provided parameter.
     * @param tipo The type of the Rol. String.
     */
    public Rol(String tipo) {
        this.tipo = tipo;
    }

    /**
     * This method adds a Usuario instance to the list of usuarios associated with this Rol instance.
     * It also sets the Rol of the provided Usuario instance to this Rol instance.
     * @param usuario The Usuario instance to be added to the list of usuarios. Instance of the Usuario class.
     */
    public void addUsuario(Usuario usuario) {
        usuarios.add(usuario);
        usuario.setRol(this);
    }

    /**
     * This method removes a Usuario instance from the list of usuarios associated with this Rol instance.
     * It also sets the Rol of the provided Usuario instance to null.
     * @param usuario The Usuario instance to be removed from the list of usuarios. Instance of the Usuario class.
     */
    public void removeUsuario(Usuario usuario) {
        usuarios.remove(usuario);
        usuario.setRol(null);
    }
}
