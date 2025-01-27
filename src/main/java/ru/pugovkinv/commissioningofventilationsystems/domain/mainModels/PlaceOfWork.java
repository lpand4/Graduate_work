package ru.pugovkinv.commissioningofventilationsystems.domain.mainModels;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Рабочий объект
 */
@Entity
@Data
@Table(name = "placeOfWork")
public class PlaceOfWork {
    /**
     * Уникальный айди объекта
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long objectId;
    /**
     * Название объекта
     */
    private String nameOfObject;
    /**
     * Адресс объекта
     */
    private String addressOfObject;
    /**
     * Список систем, которые относятся к данному объекту
     */
    @OneToMany(mappedBy = "placeOfWork")
    private List<VentilationSystem> ventilationSystems;
}
