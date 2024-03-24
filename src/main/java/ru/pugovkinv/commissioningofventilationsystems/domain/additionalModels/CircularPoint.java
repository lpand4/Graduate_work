package ru.pugovkinv.commissioningofventilationsystems.domain.additionalModels;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.Point;

@Entity
@Getter
@DiscriminatorValue("CIRCULAR")
public class CircularPoint extends Point {

    private Double diameter;


}
