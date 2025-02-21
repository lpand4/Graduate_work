package ru.pugovkinv.commissioningofventilationsystems.domain.mainModels;

import jakarta.persistence.*;
import lombok.Data;
import ru.pugovkinv.commissioningofventilationsystems.domain.enums.TypeMeasuring;
import ru.pugovkinv.commissioningofventilationsystems.domain.enums.TypeOfHole;

import java.util.List;

/**
 * Класс отвечающий за точку измерения
 */
@Entity
@Inheritance
@Table(name = "points")
@Data
public class Point {
    /**
     * Уникальное айди точки измерения
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;
    /**
     * Вентиляционная система к которой принадлежит точка измерения
     */
    @ManyToOne @JoinColumn(name = "ventilation_system_id")
    private VentilationSystem ventilationSystem;
    /**
     * Название точки измерения(Например Этаж 1, т. 1)
     */
    private String nameOfPoint;
    /**
     * Тип измерения (На решетке или в канале)
     */
    @Enumerated(EnumType.STRING)
    private TypeMeasuring typeMeasuring;
    /**
     * Тип отвертия измерения (Круглый воздуховод/решетка или прямоугольный)
     */
    @Enumerated(EnumType.STRING)
    private TypeOfHole typeOfHole;
    /**
     * Площадь сечения точки измерения (Если круглый, то (3,14*D^2)/4, если прямоугольный, то W*H)
     */
    private Double crossSectionalArea;
    /**
     * Объем воздуха, который должен быть на данной точке по проекту)
     */
    private Double airVolume;
    /**
     * Скорость потока воздуха в точке измерения по проекту
     */
    private Double airFlowRate;
    /**
     * Текущая скорость потока воздуха в данной точке
     */
    private Double currentAirFlowRate;
    /**
     * Текущий объем воздуха
     */
    private Double currentAirVolume;
    /**
     * Расхождение проектного от текущего
     */
    private Double discrepancy;
    /**
     * Измерения в данной точке, которые могли происходить при разных условиях
     */
    @OneToMany(mappedBy = "pointId")
    private List<Measurements> listAirFlowRate;

//    @PrePersist
//    private void createPoint() {
//        if(typeOfHole==TypeOfHole.CIRCULAR){
//            this.crossSectionalArea = getD
//        }
//    }
}
