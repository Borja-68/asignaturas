package Entidades;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.List;

public class Libros_Autores {

    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL)
    private List<Autores> autores;
}
