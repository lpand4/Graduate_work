package ru.pugovkinv.commissioningofventilationsystems.domain.mainModels;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "place_of_work")
public class PlaceOfWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long objectId;

    @OneToMany(mappedBy = "placeOfWork")
    private List<VentilationSystem> ventilationSystems;
}
