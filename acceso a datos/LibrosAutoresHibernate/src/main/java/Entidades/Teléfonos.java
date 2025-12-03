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
public class Teléfonos {
    @Id
    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "au_dni")
    private Autores autor;

    @NonNull
    private String NumeroTf;


}

