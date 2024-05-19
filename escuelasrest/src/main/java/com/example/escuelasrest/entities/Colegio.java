package com.example.escuelasrest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table (name = "colegio" )
public class Colegio {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column()
        private String nombre;

        @Column(length = 30)
        private String provincia;

        @Column()
        private String localidad;

        @Column(length = 100)
        private String denominacion;

        @Setter
        @Column(length = 20)
        private String naturaleza;

        @Setter
        @Column
        private Boolean comedor;

        @Column
        private Boolean concierto;

        @Column()
        private String email;

        @Column()
        private String web;

        @Column(length = 255)
        private String descripcion;

        @Column(length = 255)
        private String direccion;

        @Column(length = 18)
        private String telefono;

        @Setter
        @Column(length = 255)
        private String localizacion;

        @Setter
        @Column
        private String codigo;

        @Setter
        @Column
        private Boolean activo;

        @Setter
        @Column
        private Long desactivaciones;

        @OneToMany(mappedBy = "colegio", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonIgnore
        private List<Imagen> imagenes = new ArrayList<>();

        @OneToMany(mappedBy = "colegio", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonIgnore
        private List<Oferta> ofertas = new ArrayList<>();

        @ManyToOne
        @JoinColumn(name = "usuario_id", nullable = false)
        @JsonIgnore
        private Usuario usuario;

        @OneToMany(mappedBy = "colegio", cascade = CascadeType.ALL, orphanRemoval = false)
        @JsonIgnore
        private List<Comentario> comentarios = new ArrayList<>();

        @OneToMany(mappedBy = "colegio", cascade = CascadeType.ALL, orphanRemoval = false)
        @JsonIgnore
        private List<Valoracion> valoraciones = new ArrayList<>();

        @OneToMany(mappedBy = "colegio", cascade = CascadeType.ALL, orphanRemoval = false)
        @JsonIgnore
        private List<Respuesta> respuestas = new ArrayList<>();

        public void setUsuario(Usuario usuario) {
                this.usuario = usuario;
        }



        /**
         * The default constructor of the Colegio class.
         */
        public Colegio() {
        }


        /**
         * A constructor for the Colegio class that initializes all the fields.
         * @param id The ID of the Colegio. Long.
         * @param nombre The name of the Colegio. String.
         * @param provincia The province of the Colegio. String.
         * @param localidad The locality of the Colegio. String.
         * @param denominacion The denomination of the Colegio. String.
         * @param naturaleza The nature of the Colegio. String.
         * @param comedor The dining status of the Colegio. Boolean.
         * @param concierto The concert status of the Colegio. Boolean.
         * @param email The email of the Colegio. String.
         * @param web The web address of the Colegio. String.
         * @param descripcion The description of the Colegio. String.
         * @param direccion The address of the Colegio. String.
         * @param telefono The phone number of the Colegio. String.
         * @param imagenes The list of images associated with the Colegio. List of Imagen objects.
         * @param ofertas The list of offers associated with the Colegio. List of Oferta objects.
         * @param usuario The user associated with the Colegio. Usuario object.
         * @param comentarios The list of comments associated with the Colegio. List of Comentario objects.
         * @param valoraciones The list of ratings associated with the Colegio. List of Valoracion objects.
         * @param respuestas The list of responses associated with the Colegio. List of Respuesta objects.
         * @param codigo The code of the Colegio. String.
         * @param activo The active status of the Colegio. Boolean.
         */
        public Colegio(Long id, String nombre, String provincia, String localidad,
                       String denominacion, String naturaleza,
                       Boolean comedor, Boolean concierto, String email,
                       String web, String descripcion,
                       String direccion, String telefono, List<Imagen> imagenes,
                       List<Oferta> ofertas, Usuario usuario, List<Comentario> comentarios,
                       List<Valoracion> valoraciones, List<Respuesta> respuestas, String codigo, Boolean activo,
                       Long desactivaciones) {
                this.id = id;
                this.nombre = nombre;
                this.provincia = provincia;
                this.localidad = localidad;
                this.denominacion = denominacion;
                this.naturaleza = naturaleza;
                this.comedor = comedor;
                this.concierto = concierto;
                this.email = email;
                this.web = web;
                this.descripcion = descripcion;
                this.direccion = direccion;
                this.telefono = telefono;
                this.imagenes = imagenes;
                this.ofertas = ofertas;
                this.usuario = usuario;
                this.comentarios = comentarios;
                this.valoraciones = valoraciones;
                this.respuestas = respuestas;
                this.codigo = codigo;
                this.activo = activo;
                this.desactivaciones = desactivaciones;
        }



        public void setNombre(String nombre) {
                this.nombre = nombre;
        }

        public void setProvincia(String provincia) {
                this.provincia = provincia;
        }

        public void setLocalidad(String localidad) {
                this.localidad = localidad;
        }

        public void setComedor(Boolean comedor) {
                if (comedor == null) {
                        this.comedor = false;
                } else {
                        this.comedor = comedor;
                }
        }
        public void setDenominacion(String denominacion) {
                this.denominacion = denominacion;
        }

        public void setConcierto(Boolean concierto) {
                if( concierto == null) {
                        this.concierto = false;
                }else {
                        this.concierto = concierto;
                }
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public void setWeb(String web) {
                this.web = web;
        }

        public void setDescripcion(String descripcion) {
                this.descripcion = descripcion;
        }

        public void setDireccion(String direccion) {
                this.direccion = direccion;
        }

        public void setTelefono(String telefono) {
                this.telefono = telefono;
        }

        public void setImagenes(List<Imagen> imagenes) {
                this.imagenes = imagenes;
        }

        public void setOfertas(List<Oferta> ofertas) {
                this.ofertas = ofertas;
        }

        /**
         * This method adds a Comentario to the list of comentarios associated with the Colegio.
         * It also sets the Colegio of the Comentario to this Colegio.
         * @param comentario The Comentario to be added. This should be an instance of the Comentario class.
         */
        public void addComentario(Comentario comentario) {
            if (!comentarios.contains(comentario)) {
                comentarios.add(comentario);
                comentario.setColegio(this);
            }
        }

        /**
         * This method removes a Comentario from the list of comentarios associated with the Colegio.
         * If the Comentario is successfully removed, it also checks if the Colegio associated with the Comentario is this Colegio.
         * If so, it sets the Colegio of the Comentario to null.
         * @param comentario The Comentario to be removed. Instance of the Comentario class.
         */
        public void removeComentario(Comentario comentario) {
            if (comentarios.remove(comentario)) {
                if (comentario.getColegio() == this) {
                    comentario.setColegio(null);
                }
            }
        }

        /**
         * This method adds a Valoracion to the list of valoraciones associated with the Colegio.
         * It also sets the Colegio of the Valoracion to this Colegio.
         * @param valoracion The Valoracion to be added. Instance of the Valoracion class.
         */
        public void addValoracion(Valoracion valoracion) {
            if (!this.valoraciones.contains(valoracion)) {
                this.valoraciones.add(valoracion);
                valoracion.setColegio(this);
            }
        }

        /**
         * This method removes a Valoracion from the list of valoraciones associated with the Colegio.
         * If the Valoracion is successfully removed, it also checks if the Colegio associated with the Valoracion is this Colegio.
         * If so, it sets the Colegio of the Valoracion to null.
         * @param valoracion The Valoracion to be removed. Instance of the Valoracion class.
         */
        public void removeValoracion(Valoracion valoracion) {
            if (this.valoraciones.remove(valoracion)) {
                if (valoracion.getColegio() == this) {
                    valoracion.setColegio(null);
                }
            }
        }

        public void setRespuestas(List<Respuesta> respuestas) {
                this.respuestas = respuestas;
        }

        /**
         * This method adds a Respuesta to the list of respuestas associated with the Colegio.
         * It also sets the Colegio of the Respuesta to this Colegio.
         * @param respuesta The Respuesta to be added. Instance of the Respuesta class.
         */
        public void addRespuesta(Respuesta respuesta) {
            if (!this.respuestas.contains(respuesta)) {
                this.respuestas.add(respuesta);
                respuesta.setColegio(this);
            }
        }

        /**
         * This method removes a Respuesta from the list of respuestas associated with the Colegio.
         * If the Respuesta is successfully removed, it also checks if the Colegio associated with the Respuesta is this Colegio.
         * If so, it sets the Colegio of the Respuesta to null.
         * @param respuesta The Respuesta to be removed. This should be an instance of the Respuesta class.
         */
        public void removeRespuesta(Respuesta respuesta) {
            if (this.respuestas.remove(respuesta)) {
                if (respuesta.getColegio() == this) {
                    respuesta.setColegio(null);
                }
            }
        }


    public boolean isActivo() {
        return activo;
    }
}

