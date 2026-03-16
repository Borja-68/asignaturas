package com.example.ej501;

import org.springframework.stereotype.Service;

@Service
public class SaludoServicio {
    public String saludo(String nombre){
        return "Hola "+nombre;
    }
}
