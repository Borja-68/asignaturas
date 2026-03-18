package com.example.plantillaSpring;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioProducto {
    private List<Producto> productos=new ArrayList<>();
    private long numeroId=1L;

    public List<Producto> obtenerProductos(){
        return productos;
    }
    public Producto obtenerProductoPorId(long id){
        return productos.stream()
                .filter(producto -> producto.getId()==id)
                .findFirst()
                .orElse(null);
    }

    public void nuevoProducto(Producto prod){
        prod.setId(numeroId++);
        productos.add(prod);
    }

    public void quitaproductoPorId(long id){
        for (Producto prod: productos){
            if (prod.getId()==id){
                productos.remove(prod);
                break;
            }
        }
    }
}
