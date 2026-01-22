package Entidades;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "uso")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Uso {
    @EmbeddedId
    @NonNull
    private UsoPK id;

    @ManyToOne
    @NonNull
    @MapsId("codigoCiclo")
    @JoinColumn(name = "ciclo_codigo")
    private Ciclo cicloUso;

    @ManyToOne
    @NonNull
    @MapsId("codigoTaller")
    @JoinColumn(name = "taller_codigo")
    private Taller tallerUso;

    @NonNull
    @Column(name = "hora", columnDefinition = "Time")
    private LocalDateTime hora;

    @NonNull
    @Column(name = "fecha",columnDefinition = "Date")
    private LocalDate fecha;

    public void setCicloUso(@NonNull Ciclo cicloUso) {
        this.cicloUso = cicloUso;
        cicloUso.addUso(this);
    }

    public void setTallerUso(@NonNull Taller taller) {
        this.tallerUso = taller;
        tallerUso.addUso(this);
    }
}
