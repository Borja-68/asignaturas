package com.example.plantillaSpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/saludo")
public class SaludoControlador {

    private final SaludoServicio saludoServicio;
     @Autowired
        public SaludoControlador(SaludoServicio sal){
         this.saludoServicio=sal;
     }

     @GetMapping("/{nombre}")
    public ResponseEntity<String> saludar(@PathVariable String nombre){
         String saludo= saludoServicio.saludo(nombre);
         return ResponseEntity.ok(saludo);
     }
     @PostMapping("/{nombre}-{idioma}")
    public  ResponseEntity<String> saludar(@PathVariable String nombre,@PathVariable String idioma){
         String saludo= saludoServicio.saludarUsuario(nombre,idioma);
         return ResponseEntity.ok(saludo);
     }

    @PostMapping("/cuerpo")
    public ResponseEntity<String> saludo(@RequestBody Info info){
        String saludo= saludoServicio.saludarUsuario(info.getNombre(),info.getIdioma());
        return ResponseEntity.ok(saludo);
    }

}
