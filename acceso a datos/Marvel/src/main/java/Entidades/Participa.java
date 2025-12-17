package Entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Participa {
    @EmbeddedId
    ParticipaPK id;


    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("personajeId")
    @JoinColumn(name = "id_personaje")
    private Personaje personajeEvento;


    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("eventoId")
    @JoinColumn(name = "id_evento")
    private Evento eventoPersonaje;

    @NonNull
    @Column(columnDefinition = "DATE")
    private String fecha;
    @NonNull
    private String rol;



    public void setPersonaje(Personaje personaje){
        this.personajeEvento=personaje;
        personaje.addEvento(this);
    }

    public void setEvento(Evento evento){
        this.eventoPersonaje=evento;
        evento.addPersonaje(this);
    }

    @Override
    public String toString(){
        return personajeEvento.getId()+" "+eventoPersonaje.getId()+" "+fecha+" "+rol;
    }
}
