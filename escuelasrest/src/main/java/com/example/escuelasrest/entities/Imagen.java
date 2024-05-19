package com.example.escuelasrest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.engine.jdbc.BlobProxy;

import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;

@Getter
@Entity
@Table (name = "imagen")
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column
    @Lob
    @JsonIgnore
    private Blob url;

    public Imagen(String url, String descripcion, Colegio colegio) {
        try {
            this.url = BlobProxy.generateProxy(url.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir String a Blob", e);
        }
        this.descripcion = descripcion;
        this.colegio = colegio;
    }


    @Transient  // this means it won't be persisted in the database
    public String getUrlAsString() {
        if (this.url != null) {
            try {
                byte[] bytes = this.url.getBytes(1, (int) this.url.length());
                return new String(bytes, StandardCharsets.UTF_8);
            } catch (SQLException e) {
                throw new RuntimeException("Error al convertir Blob a String", e);
            }
        }
        return null;
    }

    @Setter
    @Column(length = 255)
    private String descripcion;

    @Setter
    @ManyToOne
    private Colegio colegio;

    @Setter
    @Column
    private Boolean activo;


    /**
     * Default constructor for the Imagen class.
     */
    public Imagen() {
    }

    /**
     * Overloaded constructor for the Imagen class.
     * This constructor initializes a new Imagen instance with the provided parameters.
     * @param url The URL of the Imagen. Instance of Blob.
     * @param descripcion The description of the Imagen. String.
     * @param colegio The school associated with the Imagen. Instance of the Colegio class.
     * @param activo The status of the Imagen. Boolean.
     */
    public Imagen(Blob url, String descripcion, Colegio colegio, Boolean activo) {
        this.url = url;
        this.descripcion = descripcion;
        this.colegio = colegio;
        this.activo = activo;
    }


    public boolean isActivo() {
        return activo;
    }
}
