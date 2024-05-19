package com.example.escuelasrest.repositories;

import com.example.escuelasrest.entities.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepository extends JpaRepository <Imagen, Long> {

}
