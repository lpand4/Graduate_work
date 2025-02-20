package ru.pugovkinv.commissioningofventilationsystems.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.PlaceOfWork;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.Point;

import java.util.List;

@Data
public class VentilationSystemDto {
    /**
     * Уникальное айди вентиляционной системы
     */
    private Long ventilationSystemId;
    /**
     * Название системы (Например, В1 - коридоры корпуса А)
     */
    @NotEmpty(message = "Название системы не должно быть пустым!")
    private String nameOfSystem;
    /**
     * Полный расход объема воздуха который система должна выдавать по проекту
     */
    @NotEmpty(message = "Полный расход воздуха системы не должно быть пустым!")
    @Pattern(regexp = "^-?(0|[1-9][0-9]*)(?:[.]\\d+|)$", message = "Полный расход воздуха системы должен иметь числовое значение," +
            " и, если оно не целое, то запись через точку!")
    private String fullAirVolume;
    /**
     * Список точек измерений, которые относятся к данной системе
     */
    private List<Point> pointsOfSystem;
    /**
     * Рабочий объект на котором находится данная система (Например, РУДН)
     */
    private PlaceOfWork placeOfWork;
}
