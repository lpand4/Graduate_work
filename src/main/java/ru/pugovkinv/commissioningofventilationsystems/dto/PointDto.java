package ru.pugovkinv.commissioningofventilationsystems.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import ru.pugovkinv.commissioningofventilationsystems.domain.enums.TypeMeasuring;
import ru.pugovkinv.commissioningofventilationsystems.domain.enums.TypeOfHole;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.Measurements;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.VentilationSystem;

import java.util.List;

@Data
public class PointDto {
    /**
     * Уникальное айди точки измерения
     */
    private Long pointId;
    /**
     * Вентиляционная система к которой принадлежит точка измерения
     */
    private VentilationSystem ventilationSystem;
    /**
     * Название точки измерения(Например Этаж 1, т. 1)
     */
    @NotEmpty(message = "Название точки измерения не должно быть пустым!")
    private String nameOfPoint;
    /**
     * Тип измерения (На решетке или в канале)
     */
    private TypeMeasuring typeMeasuring;
    /**
     * Тип отвертия измерения (Круглый воздуховод/решетка или прямоугольный)
     */
    private TypeOfHole typeOfHole;
    /**
     * Площадь сечения точки измерения (Если круглый, то (3,14*D^2)/4, если прямоугольный, то W*H)
     */
    @NotEmpty(message = "Название точки измерения не должно быть пустым!")
    private String crossSectionalArea;
    /**
     * Объем воздуха, который должен быть на данной точке по проекту)
     */
    @NotEmpty(message = "Объем воздуха точки измерения не должен быть пустым!")
    @Pattern(regexp = "^-?(0|[1-9][0-9]*)(?:[.]\\d+|)$", message = "Объем воздуха точки измерения должен иметь числовое значение," +
            " и, если оно не целое, то запись через точку!")
    private String airVolume;
    /**
     * Скорость потока воздуха в точке измерения по проекту
     */
    @NotEmpty(message = "Скорость потока воздуха точки измерения не должен быть пустым!")
    @Pattern(regexp = "^-?(0|[1-9][0-9]*)(?:[.]\\d+|)$", message = "Скорость потока воздуха точки измерения должен иметь числовое значение," +
            " и, если оно не целое, то запись через точку!")
    private String  airFlowRate;
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
    private List<Measurements> listAirFlowRate;

}
