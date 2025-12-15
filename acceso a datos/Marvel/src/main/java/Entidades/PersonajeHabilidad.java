package Entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "Personaje_Habilidad")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class PersonajeHabilidad {
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_personaje")
    private Personaje personajeHabilidad;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_habilidad")
    private Habilidad habilidadPersonaje;

    public void setPersonaje(Personaje personaje){
        this.personajeHabilidad=personaje;
        personaje.addHabilidad(this);
    }
    public void setHabilidad(Habilidad habilidad){
        this.habilidadPersonaje=habilidad;
        habilidad.addPersonaje(this);
    }

    @Override
    public String toString(){
        return personajeHabilidad.getId()+" "+habilidadPersonaje.getId();
    }
}
