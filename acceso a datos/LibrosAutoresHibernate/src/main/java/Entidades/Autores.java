package Entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Autores {
    @Id
    @NonNull
    @Column(name = "au_dni")
    private String DniAutor;

    @NonNull
    private String Nombre;

    @NonNull
    private String Nacionalidad;

    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL)
    private Teléfonos telefono;


    public void nuevoTelefono(Teléfonos telefono){
        this.telefono=telefono;
        telefono.setAutor(this);
    }
}
