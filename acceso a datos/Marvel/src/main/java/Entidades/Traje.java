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
public class Traje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "Integer")
    private Integer id;
    @NonNull
    private String especificacion;
    @OneToOne(mappedBy = "traje",cascade = CascadeType.ALL)
    private Personaje heroe;

    @Override
    public String toString(){
        return id+" "+especificacion+" "+heroe.getId();
    }

}
