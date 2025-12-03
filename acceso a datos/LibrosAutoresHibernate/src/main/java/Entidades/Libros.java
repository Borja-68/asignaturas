package Entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lib_id",columnDefinition = "Integer")
    private int idLibro;

    @NonNull
    private String Titulo;

    @NonNull
    @Column(columnDefinition = "numeric(6,2)")
    private double Precio;
}
