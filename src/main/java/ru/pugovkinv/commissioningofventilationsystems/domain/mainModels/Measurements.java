package ru.pugovkinv.commissioningofventilationsystems.domain.mainModels;

import jakarta.persistence.*;
import lombok.Data;
/**
 * Измерения, которые происходят при разных условиях
 */
@Entity
@Data
@Table(name = "measurements")
public class Measurements {
    /**
     * Уникальное айди измерения
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long measurementsId;
    /**
     * Точка измерения, к которое принадлежит определенное измерение
     */
    @ManyToOne
    private Point pointId;
    /**
     * Частота частотного преобразователя, при которой проводилось измерение
     */
    private Double frequency;
    /**
     * Название измерения (Например, Измерение 1, 30% открытость клапана)
     */
    public String nameOfMeasure;

    /**
     * Значение скорости потока воздуха при данных условиях
     */
    public Double valueOfMeasure;
}
