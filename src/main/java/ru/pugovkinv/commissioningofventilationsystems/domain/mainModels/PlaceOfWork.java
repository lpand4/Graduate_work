package ru.pugovkinv.commissioningofventilationsystems.domain.mainModels;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Рабочий объект
 */
@Entity
@Data
@Table(name = "place_of_work")
public class PlaceOfWork {
    /**
     * Уникальный айди объекта
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long objectId;
    /**
     * Список систем, которые относятся к данному объекту
     */
    @OneToMany(mappedBy = "placeOfWork")
    private List<VentilationSystem> ventilationSystems;
}
