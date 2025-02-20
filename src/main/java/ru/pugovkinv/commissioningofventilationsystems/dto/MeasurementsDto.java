package ru.pugovkinv.commissioningofventilationsystems.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.Point;

@Data
public class MeasurementsDto {
    /**
     * Уникальное айди измерения
     */
    private Long measurementsId;
    /**
     * Точка измерения, к которое принадлежит определенное измерение
     */
    private Point pointId;
    /**
     * Примечание к измерению
     */
    private String note;
    /**
     * Значение скорости потока воздуха при данных условиях
     */
    @NotEmpty(message = "Значение измерения не должно быть пустым!")
    @Pattern(regexp = "^-?(0|[1-9][0-9]*)(?:[.]\\d+|)$", message = "Значение измерения должено быть числовым," +
            " и, если оно не целое, то запись через точку!")
    public String valueOfMeasure;

}
