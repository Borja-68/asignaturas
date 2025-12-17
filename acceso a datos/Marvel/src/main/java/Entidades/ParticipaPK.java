package Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ParticipaPK implements Serializable {
    private Integer personajeId;

    private Integer eventoId;
}
