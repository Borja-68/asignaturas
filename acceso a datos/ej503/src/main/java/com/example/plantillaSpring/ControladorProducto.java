package com.example.plantillaSpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ControladorProducto {
    private ServicioProducto servicioProducto;
    @Autowired
    public ControladorProducto(ServicioProducto servicioProducto){
        this.servicioProducto=servicioProducto;
    }


    @GetMapping
    public ResponseEntity<List<Producto>> obtenerProducos(){
        List<Producto> productos =servicioProducto.obtenerProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducoId(@PathVariable long id){
        Producto prod=servicioProducto.obtenerProductoPorId(id);
        return ResponseEntity.ok(prod);
    }

    @PostMapping
    public ResponseEntity<String> anadirProducto(@RequestBody Producto producto){
        servicioProducto.nuevoProducto(producto);
        return ResponseEntity.ok().body("Se creo correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> quitarProducto(@PathVariable long id) {
        Producto prod = servicioProducto.obtenerProductoPorId(id);
        if (prod != null) {
            servicioProducto.quitaproductoPorId(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}
