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
     * Примечание к измерению
     */
    private String note;
    /**
     * Значение скорости потока воздуха при данных условиях
     */
    public Double valueOfMeasure;
}
