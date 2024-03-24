package ru.pugovkinv.commissioningofventilationsystems.domain.mainModels;

import jakarta.persistence.*;
import lombok.Data;
import ru.pugovkinv.commissioningofventilationsystems.domain.enums.TypeMeasuring;
import ru.pugovkinv.commissioningofventilationsystems.domain.enums.TypeOfHole;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Inheritance
@DiscriminatorColumn(name = "typeOfHole")
@Table(name = "points")
@Data
public abstract class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    @ManyToOne
    private VentilationSystem ventilationSystem;

    private String nameOfPoint;

    private TypeMeasuring typeMeasuring;

    @Column(insertable=false, updatable=false)
    private TypeOfHole typeOfHole;

    private Double crossSectionalArea;

    private Double airVolume;

    private Double airFlowRate;
    @OneToMany(mappedBy = "pointId")
    private List<Measurements> currentAirFlowRate;

//    @PrePersist
//    private void createPoint() {
//        if(typeOfHole==TypeOfHole.CIRCULAR){
//            this.crossSectionalArea = getD
//        }
//    }
}
