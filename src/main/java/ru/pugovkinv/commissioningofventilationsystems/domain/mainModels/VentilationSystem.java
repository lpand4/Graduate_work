package ru.pugovkinv.commissioningofventilationsystems.domain.mainModels;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Вентиляционная система
 */
@Entity
@Data
@Table(name = "ventilation_systems")
public class VentilationSystem {
    /**
     * Уникальное айди вентиляционной системы
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ventilationSystemId;
    /**
     * Название системы (Например, В1 - коридоры корпуса А)
     */
    private String nameOfSystem;
    /**
     * Полный расход объема воздуха который система должна выдавать по проекту
     */
    private Double fullAirVolume;
    /**
     * Список точек измерений, которые относятся к данной системе
     */
    @OneToMany(mappedBy = "ventilationSystem")
    private List<Point> pointsOfSystem;
    /**
     * Рабочий объект на котором находится данная система (Например, РУДН)
     */
    @ManyToOne
    private PlaceOfWork placeOfWork;
}
