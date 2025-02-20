package ru.pugovkinv.commissioningofventilationsystems.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.VentilationSystem;

import java.util.List;

@Data
public class PlaceOfWorkDto {

    /**
     * Уникальный айди объекта
     */
    private Long objectId;
    /**
     * Название объекта
     */
    @NotEmpty(message = "Название объекта должно быть не пустым!")
    private String nameOfObject;
    /**
     * Адресс объекта
     */
    @NotEmpty(message = "Адресс объекта должен быть не пустым!")
    private String addressOfObject;
    /**
     * Список систем, которые относятся к данному объекту
     */
    private List<VentilationSystem> ventilationSystems;
}
