package ru.pugovkinv.commissioningofventilationsystems.dto;

import org.springframework.stereotype.Component;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.Measurements;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.PlaceOfWork;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.Point;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.VentilationSystem;


/**
 * Преобразователь Dto в сущности и наоборот
 */
@Component
public class DtoMapper {
    /**
     * Преобразование объекта в его dto
     *
     * @param placeOfWork объект
     * @return его dto
     */
    public PlaceOfWorkDto toPlaceOfWorkDto(PlaceOfWork placeOfWork) {
        PlaceOfWorkDto placeOfWorkDto = new PlaceOfWorkDto();
        placeOfWorkDto.setObjectId(placeOfWork.getObjectId());
        placeOfWorkDto.setNameOfObject(placeOfWork.getNameOfObject());
        placeOfWorkDto.setAddressOfObject(placeOfWork.getAddressOfObject());
        placeOfWorkDto.setVentilationSystems(placeOfWork.getVentilationSystems());
        return placeOfWorkDto;
    }

    /**
     * Преобразование вент. системы в ее dto
     *
     * @param ventilationSystem вент. система
     * @return ее dto
     */
    public VentilationSystemDto toVentilationSystemDto(VentilationSystem ventilationSystem) {
        VentilationSystemDto ventilationSystemDto = new VentilationSystemDto();
        ventilationSystemDto.setVentilationSystemId(ventilationSystem.getVentilationSystemId());
        ventilationSystemDto.setNameOfSystem(ventilationSystem.getNameOfSystem());
        ventilationSystemDto.setFullAirVolume(String.valueOf(ventilationSystem.getFullAirVolume()));
        ventilationSystemDto.setPointsOfSystem(ventilationSystem.getPointsOfSystem());
        ventilationSystemDto.setPlaceOfWork(ventilationSystem.getPlaceOfWork());
        return ventilationSystemDto;
    }

    /**
     * Преобразование точки измерения в ее dto
     *
     * @param point точка измерения
     * @return ее dto
     */
    public PointDto toPointDto(Point point) {
        PointDto pointDto = new PointDto();
        pointDto.setPointId(point.getPointId());
        pointDto.setVentilationSystem(point.getVentilationSystem());
        pointDto.setNameOfPoint(point.getNameOfPoint());
        pointDto.setTypeMeasuring(point.getTypeMeasuring());
        pointDto.setTypeOfHole(point.getTypeOfHole());
        pointDto.setCrossSectionalArea(String.valueOf(point.getCrossSectionalArea()));
        pointDto.setAirVolume(String.valueOf(point.getAirVolume()));
        pointDto.setAirFlowRate(String.valueOf(point.getAirFlowRate()));
        pointDto.setCurrentAirFlowRate(point.getCurrentAirFlowRate());
        pointDto.setCurrentAirVolume(point.getCurrentAirVolume());
        pointDto.setDiscrepancy(point.getDiscrepancy());
        pointDto.setListAirFlowRate(point.getListAirFlowRate());
        return pointDto;
    }

    /**
     * Преобразование измерения в его dto
     *
     * @param measurements измерение
     * @return его dto
     */
    public MeasurementsDto toMeasurementsDto(Measurements measurements) {
        MeasurementsDto measurementsDto = new MeasurementsDto();
        measurementsDto.setMeasurementsId(measurements.getMeasurementsId());
        measurementsDto.setPointId(measurements.getPointId());
        measurementsDto.setNote(measurements.getNote());
        measurementsDto.setValueOfMeasure(String.valueOf(measurements.getValueOfMeasure()));
        return measurementsDto;
    }

    /**
     * Преобразование dto объекта в объект
     *
     * @param placeOfWorkDto dto объекта
     * @return объект
     */
    public PlaceOfWork toPlaceOfWork(PlaceOfWorkDto placeOfWorkDto) {
        PlaceOfWork placeOfWork = new PlaceOfWork();
        placeOfWork.setObjectId(placeOfWorkDto.getObjectId());
        placeOfWork.setNameOfObject(placeOfWorkDto.getNameOfObject());
        placeOfWork.setAddressOfObject(placeOfWorkDto.getAddressOfObject());
        placeOfWork.setVentilationSystems(placeOfWorkDto.getVentilationSystems());
        return placeOfWork;
    }

    /**
     * Преобразование dto вент. системы в вент. систему
     *
     * @param ventilationSystemDto dto вент. системы
     * @return вент. система
     */
    public VentilationSystem toVentilationSystem(VentilationSystemDto ventilationSystemDto) {
        VentilationSystem ventilationSystem = new VentilationSystem();
        ventilationSystem.setVentilationSystemId(ventilationSystemDto.getVentilationSystemId());
        ventilationSystem.setNameOfSystem(ventilationSystemDto.getNameOfSystem());
        ventilationSystem.setFullAirVolume(Double.parseDouble(ventilationSystemDto.getFullAirVolume()));
        ventilationSystem.setPointsOfSystem(ventilationSystemDto.getPointsOfSystem());
        ventilationSystem.setPlaceOfWork(ventilationSystemDto.getPlaceOfWork());
        return ventilationSystem;
    }

    /**
     * Преобразование dto точки измерения в точку измерения
     *
     * @param pointDto dto точки измерения
     * @return точка измерения
     */
    public Point toPoint(PointDto pointDto) {
        Point point = new Point();
        point.setPointId(pointDto.getPointId());
        point.setVentilationSystem(pointDto.getVentilationSystem());
        point.setNameOfPoint(pointDto.getNameOfPoint());
        point.setTypeMeasuring(pointDto.getTypeMeasuring());
        point.setTypeOfHole(pointDto.getTypeOfHole());
        point.setCrossSectionalArea(Double.valueOf(pointDto.getCrossSectionalArea()));
        point.setAirVolume(Double.valueOf(pointDto.getAirVolume()));
        point.setAirFlowRate(Double.valueOf(pointDto.getAirFlowRate()));
        point.setCurrentAirFlowRate(pointDto.getCurrentAirFlowRate());
        point.setCurrentAirVolume(pointDto.getCurrentAirVolume());
        point.setDiscrepancy(pointDto.getDiscrepancy());
        point.setListAirFlowRate(pointDto.getListAirFlowRate());
        return point;
    }

    /**
     * Преобразование dto измерения в измерение
     *
     * @param measurementsDto dto измерения
     * @return измерение
     */
    public Measurements toMeasurements(MeasurementsDto measurementsDto) {
        Measurements measurements = new Measurements();
        measurements.setMeasurementsId(measurementsDto.getMeasurementsId());
        measurements.setPointId(measurementsDto.getPointId());
        measurements.setNote(measurementsDto.getNote());
        measurements.setValueOfMeasure(Double.valueOf(measurementsDto.getValueOfMeasure()));
        return measurements;
    }
}
