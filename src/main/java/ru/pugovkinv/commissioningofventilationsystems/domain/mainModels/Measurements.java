package ru.pugovkinv.commissioningofventilationsystems.domain.mainModels;

import jakarta.persistence.*;
import lombok.Data;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.Point;

@Entity
@Data
@Table(name = "measurements")
public class Measurements{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long measurementsId;

    @ManyToOne
    private Point pointId;

    private Double frequency;

    public String nameOfMeasure;

    public Double valueOfMeasure;
}
