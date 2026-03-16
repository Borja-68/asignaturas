package com.example.plantillaSpring;

import org.springframework.stereotype.Service;

@Service
public class SaludoServicio {
    public String saludo(String nombre){
        return "Hola "+nombre;
    }
    public String saludarUsuario(String nombre, String idioma) {
        switch(idioma){
            case "ESPAÑOL" -> {
                return "¡Hola, " + nombre + "!";
            }
            case "INGLES" -> {
                return "Hello, " + nombre + "!";
            }
            case "FRANCES" -> {
                return "Bonjour, " + nombre + "!";
            }
            case "ITALIANO" -> {
                return "Ciao, " + nombre + "!";
            }
            default -> {
                return "Saludo no soportado para el idioma especificado";
            }
        }
    }
}
