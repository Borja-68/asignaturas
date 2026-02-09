package Entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "director")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = true,columnDefinition = "Varchar(255)")
    private String codigoCuerpo;

    @NonNull
    @Column(columnDefinition = "int(11)")
    private int edad;

    @Column(nullable = true,columnDefinition = "Varchar(255)")
    private String nombre;

    @OneToOne(mappedBy = "director")
    private Instituto instituto;
}
