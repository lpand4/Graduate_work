package ru.pugovkinv.commissioningofventilationsystems.domain.mainModels;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "ventilation_systems")
public class VentilationSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ventilationSystemId;

    private String nameOfSystem;

    private Double fullAirVolume;

    @OneToMany(mappedBy = "ventilationSystem")
    private List<Point> pointsOfSystem;

    @ManyToOne
    private PlaceOfWork placeOfWork;
}
