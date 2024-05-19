package com.example.escuelasrest.services;

import com.example.escuelasrest.repositories.ImagenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagenService {
    @Autowired
    private ImagenRepository repository;


}
