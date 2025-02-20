package ru.pugovkinv.commissioningofventilationsystems.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.pugovkinv.commissioningofventilationsystems.domain.enums.TypeMeasuring;
import ru.pugovkinv.commissioningofventilationsystems.domain.enums.TypeOfHole;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.Measurements;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.PlaceOfWork;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.Point;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.VentilationSystem;
import ru.pugovkinv.commissioningofventilationsystems.dto.*;
import ru.pugovkinv.commissioningofventilationsystems.services.MeasurementsService;
import ru.pugovkinv.commissioningofventilationsystems.services.PlaceOfWorkService;
import ru.pugovkinv.commissioningofventilationsystems.services.PointService;
import ru.pugovkinv.commissioningofventilationsystems.services.VentilationSystemService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/commissioning")
public class CommissioningController {
    private final PlaceOfWorkService placeOfWorkService;
    private final VentilationSystemService ventilationSystemService;
    private final PointService pointService;
    private final MeasurementsService measurementsService;
    private final DtoMapper dtoMapper;

    /**
     * Компаратор для сортировки точек измерения по названию
     */
    public static Comparator<Point> pointNameComparator = new Comparator<Point>() {
        /**
         * Проверка строки на то, является ли она числом
         * @param str входная строка
         * @return да/нет
         */
        public static boolean isDigit(String str) {
            try {
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        /**
         * Сравнение по названию, если в названии не числа,
         * то это выигрышная позиция, остальные числа по возрастанию
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return выигрышный объект
         */
        @Override
        public int compare(Point o1, Point o2) {
            if (isDigit(o1.getNameOfPoint()) && isDigit(o2.getNameOfPoint())) {
                if (Integer.parseInt(o1.getNameOfPoint()) > Integer.parseInt(o2.getNameOfPoint())) {
                    return 1;
                } else if (Integer.parseInt(o1.getNameOfPoint()) < Integer.parseInt(o2.getNameOfPoint())) {
                    return -1;
                } else if (Integer.parseInt(o1.getNameOfPoint()) == Integer.parseInt(o2.getNameOfPoint())) {
                    return 0;
                }
            } else if (isDigit(o1.getNameOfPoint()) && !isDigit(o2.getNameOfPoint())) {
                return 1;
            } else if (isDigit(o2.getNameOfPoint()) && !isDigit(o1.getNameOfPoint())) {
                return -1;
            } else {
                return 0;
            }
            return 0;
        }
    };


    @GetMapping("/")
    public String home() {
        return "home";
    }

    //region Код под тип PlaceOfWork

    /**
     * Вывод всех доступных объектов наладки
     *
     * @param model модель
     * @return страница с списком объектов наладки
     */
    @GetMapping("/objects")
    public String getAllObjects(Model model) {
        List<PlaceOfWork> placeOfWorkList = placeOfWorkService.findAll();
        model.addAttribute("objects", placeOfWorkList.stream()
                .map(dtoMapper::toPlaceOfWorkDto)
                .collect(Collectors.toList()));
        return "object_main_page";
    }

    /**
     * Страница добавления нового объекта наладки
     *
     * @param model модель
     * @return страница создания нового объекта
     */
    @GetMapping("/objects/add")
    public String addObject(Model model) {
        model.addAttribute("new_object", new PlaceOfWorkDto());
        return "object_add_page";
    }

    /**
     * Отправление нового объекта в БД
     *
     * @param newObject новый объект
     * @return возвращает на страницу с списком объектов
     */
    @PostMapping("/objects/add")
    public String addObject(@ModelAttribute("new_object") @Validated PlaceOfWorkDto newObject, BindingResult br) {
        if (br.hasErrors()){
            return "object_add_page";
        }
        placeOfWorkService.save(dtoMapper.toPlaceOfWork(newObject));
        return "redirect:/commissioning/objects";
    }

    /**
     * Удаление объекта наладки по кнопке
     *
     * @param id айди нужного объекта
     * @return возвращает на страницу с списком объектов
     */
    @GetMapping("/objects/delete/{objectId}")
    public String deleteObject(@PathVariable("objectId") Long id) {
        placeOfWorkService.deleteById(id);
        return "redirect:/commissioning/objects";
    }

    /**
     * Форсированное удаление объекта
     *
     * @param objectId айди нужного объекта
     * @return страница с объектами
     */
    @GetMapping("/objects/forcedelete/{objectId}")
    public String forceDeleteObject(@PathVariable("objectId") Long objectId) {
        PlaceOfWork placeOfWork = placeOfWorkService.findById(objectId).get();
        for (VentilationSystem vent : placeOfWork.getVentilationSystems()) {
            forceDeleteVent(objectId, vent.getVentilationSystemId());
        }
        placeOfWorkService.deleteById(objectId);
        return "redirect:/commissioning/objects";
    }

    /**
     * Изменение данных объекта наладки
     *
     * @param id    айди необходимого объекта
     * @param model модель
     * @return страница изменения объекта наладки
     */
    @GetMapping("/objects/update/{objectId}")
    public String updateObject(@PathVariable("objectId") Long id, Model model) {
        PlaceOfWork placeOfWorkToUpdate = placeOfWorkService.findById(id).get();
        model.addAttribute("update_object", dtoMapper.toPlaceOfWorkDto(placeOfWorkToUpdate));
        return "object_update_page";
    }

    /**
     * Отправка измененного объекта
     *
     * @param updatedObject измененный объект
     * @return возвращает на страницу с списком объектов
     */
    @PostMapping("/objects/update")
    public String updateObject(@ModelAttribute("update_object") @Validated PlaceOfWorkDto updatedObject, BindingResult br) {
        if (br.hasErrors()){
            return "object_update_page";
        }
        placeOfWorkService.updatePlaceOfWork(dtoMapper.toPlaceOfWork(updatedObject));
        return "redirect:/commissioning/objects";
    }
    //endregion

    // region Код под тип VentilationSystem


    /**
     * Получение всех вент. систем объекта
     *
     * @param objectId айди объекта
     * @param model    модель
     * @return возвращает страницу с списков вент. систем
     */
    @GetMapping("/objects/{objectId}/vents")
    public String getAllVents(@PathVariable Long objectId, Model model) {
        PlaceOfWork placeOfWork = placeOfWorkService.findById(objectId).get();
        List<VentilationSystem> ventilationSystemList = ventilationSystemService.findAll(placeOfWork);
        model.addAttribute("vents", ventilationSystemList
                .stream().map(dtoMapper::toVentilationSystemDto).collect(Collectors.toList()));
        model.addAttribute("object", dtoMapper.toPlaceOfWorkDto(placeOfWork));
        return "ventsystem_main_page";
    }

    /**
     * Добавление новой вент. системы
     *
     * @param objectId айди объекта
     * @param model    модель
     * @return возвращает страницу с добавлением новой вент. системы
     */
    @GetMapping("/objects/{objectId}/vents/add")
    public String addVent(@PathVariable Long objectId, Model model) {
        PlaceOfWork placeOfWork = placeOfWorkService.findById(objectId).get();
        model.addAttribute("object", dtoMapper.toPlaceOfWorkDto(placeOfWork));
        model.addAttribute("new_vent", new VentilationSystemDto());
        return "ventsystem_add_page";
    }

    /**
     * Отправка новой вент. системы в БД
     *
     * @param objectId айди объекта
     * @param newVent  новая вент. система
     * @return возвращает на страницу со списком вент. систем
     */
    @PostMapping("/objects/{objectId}/vents/add")
    public String addVent(@PathVariable Long objectId,
                          @ModelAttribute("new_vent") @Validated VentilationSystemDto newVent,
                          BindingResult br, Model model) {
        PlaceOfWork mainObject = placeOfWorkService.findById(objectId).get();
        if (br.hasErrors()){
            model.addAttribute("object", dtoMapper.toPlaceOfWorkDto(mainObject));
            return "ventsystem_add_page";
        }
        newVent.setPlaceOfWork(mainObject);
        mainObject.getVentilationSystems().add(dtoMapper.toVentilationSystem(newVent));
        placeOfWorkService.updatePlaceOfWork(mainObject);
        ventilationSystemService.save(dtoMapper.toVentilationSystem(newVent));
        return "redirect:/commissioning/objects/" + objectId + "/vents";

    }

    /**
     * Удаление вент. системы
     *
     * @param objectId            айди объекта
     * @param ventilationSystemId айди вент. системы
     * @return возвращает на страницу с списком вент. систем
     */
    @GetMapping("/objects/{objectId}/vents/delete/{ventilationSystemId}")
    public String deleteVent(@PathVariable("objectId") Long objectId,
                             @PathVariable("ventilationSystemId") Long ventilationSystemId) {
        ventilationSystemService.deleteById(ventilationSystemId);
        return "redirect:/commissioning/objects/" + objectId + "/vents";
    }

    @GetMapping("/objects/{objectId}/vents/forcedelete/{ventilationSystemId}")
    public String forceDeleteVent(@PathVariable("objectId") Long objectId,
                                  @PathVariable("ventilationSystemId") Long ventilationSystemId) {
        VentilationSystem ventilationSystem = ventilationSystemService.findById(ventilationSystemId).get();
        for (Point point : ventilationSystem.getPointsOfSystem()) {
            forceDeletePoint(objectId, ventilationSystemId, point.getPointId());
        }
        ventilationSystemService.deleteById(ventilationSystemId);
        return "redirect:/commissioning/objects/" + objectId + "/vents";
    }

    /**
     * Обновление вент. системы
     *
     * @param objectId            айди объекта
     * @param ventilationSystemId айди вент. системы
     * @param model               модель
     * @return возвращает страницу с изменением вент. системы
     */
    @GetMapping("/objects/{objectId}/vents/update/{ventilationSystemId}")
    public String updateVent(@PathVariable("objectId") Long objectId,
                             @PathVariable("ventilationSystemId") Long ventilationSystemId, Model model) {
        PlaceOfWork placeOfWork = placeOfWorkService.findById(objectId).get();
        VentilationSystem ventilationSystemToUpdate = ventilationSystemService.findById(ventilationSystemId).get();
        model.addAttribute("update_vent", dtoMapper.toVentilationSystemDto(ventilationSystemToUpdate));
        model.addAttribute("object", dtoMapper.toPlaceOfWorkDto(placeOfWork));
        return "ventsystem_update_page";
    }

    /**
     * Отправка обновленной вент. системы
     *
     * @param objectId    айди объекта
     * @param updatedVent обновленная вент. система
     * @return возвращает на страницу со списком вент. систем
     */
    @PostMapping("/objects/{objectId}/vents/update")
    public String updateVent(@PathVariable("objectId") Long objectId,
                             @ModelAttribute("update_vent") @Validated VentilationSystemDto updatedVent,
                             BindingResult br, Model model) {
        PlaceOfWork placeOfWork = placeOfWorkService.findById(objectId).get();
        if (br.hasErrors()){
            model.addAttribute("object", dtoMapper.toPlaceOfWorkDto(placeOfWork));
            return "ventsystem_update_page";
        }
        updatedVent.setPlaceOfWork(placeOfWork);
        ventilationSystemService.updateVentilationSystem(dtoMapper.toVentilationSystem(updatedVent));
        return "redirect:/commissioning/objects/" + objectId + "/vents";
    }
    //endregion

    //region Код под тип Point

    /**
     * Получение всех точек измерения
     *
     * @param objectId            айди объекта
     * @param ventilationSystemId айди системы вентиляции
     * @param model               модель
     * @return страницу с списков точек измерения
     */
    @GetMapping("/objects/{objectId}/vents/{ventilationSystemId}/points")
    public String getAllPoints(@PathVariable Long objectId, @PathVariable Long ventilationSystemId, Model model) {
        PlaceOfWork placeOfWork = placeOfWorkService.findById(objectId).get();
        VentilationSystem ventilationSystem = ventilationSystemService.findById(ventilationSystemId).get();
        List<Point> pointList = pointService.findAll(ventilationSystem);

        List<Point> points = pointList.stream().sorted(pointNameComparator).toList();
        model.addAttribute("points", points.stream().map(dtoMapper::toPointDto).collect(Collectors.toList()));
        model.addAttribute("vent", dtoMapper.toVentilationSystemDto(ventilationSystem));
        model.addAttribute("object", dtoMapper.toPlaceOfWorkDto(placeOfWork));
        return "point_main_page";
    }

    /**
     * Добавление точки измерения
     *
     * @param objectId            айди объекта
     * @param ventilationSystemId айди вент. системы
     * @param model               модель
     * @return страницу добавления точки измерения
     */
    @GetMapping("/objects/{objectId}/vents/{ventilationSystemId}/points/add")
    public String addPoint(@PathVariable Long objectId, @PathVariable Long ventilationSystemId, Model model) {
        PlaceOfWork placeOfWork = placeOfWorkService.findById(objectId).get();
        VentilationSystem ventilationSystem = ventilationSystemService.findById(ventilationSystemId).get();
        model.addAttribute("circular", TypeOfHole.CIRCULAR);
        model.addAttribute("rectangular", TypeOfHole.RECTANGULAR);
        model.addAttribute("type_meas_inside", TypeMeasuring.INSIDE_THE_DUCT);
        model.addAttribute("type_meas_on", TypeMeasuring.ON_THE_GRATE);
        model.addAttribute("vent", dtoMapper.toVentilationSystemDto(ventilationSystem));
        model.addAttribute("object", dtoMapper.toPlaceOfWorkDto(placeOfWork));
        model.addAttribute("new_point", new PointDto());
        return "point_add_page";
    }

    /**
     * Добавление точки измерения
     *
     * @param objectId            айди объекта
     * @param ventilationSystemId айди вент. системы
     * @param newPoint            новая точка измерения
     * @return страницу с списком точек измерений
     */
    @PostMapping("/objects/{objectId}/vents/{ventilationSystemId}/points/add")
    public String addPoint(@PathVariable Long objectId,
                           @PathVariable Long ventilationSystemId,
                           @ModelAttribute("new_point") @Validated PointDto newPoint,
                           BindingResult br,Model model) {
        PlaceOfWork mainObject = placeOfWorkService.findById(objectId).get();
        VentilationSystem mainVent = ventilationSystemService.findById(ventilationSystemId).get();
        if (br.hasErrors()){
            model.addAttribute("circular", TypeOfHole.CIRCULAR);
            model.addAttribute("rectangular", TypeOfHole.RECTANGULAR);
            model.addAttribute("type_meas_inside", TypeMeasuring.INSIDE_THE_DUCT);
            model.addAttribute("type_meas_on", TypeMeasuring.ON_THE_GRATE);
            model.addAttribute("vent", dtoMapper.toVentilationSystemDto(mainVent));
            model.addAttribute("object", dtoMapper.toPlaceOfWorkDto(mainObject));
            return "point_add_page";
        }
        newPoint.setVentilationSystem(mainVent);
        mainVent.getPointsOfSystem().add(dtoMapper.toPoint(newPoint));
        pointService.save(dtoMapper.toPoint(newPoint));
        ventilationSystemService.updateVentilationSystem(mainVent);
        placeOfWorkService.updatePlaceOfWork(mainObject);
        return "redirect:/commissioning/objects/" + objectId + "/vents/" + ventilationSystemId + "/points";
    }

    /**
     * Удаление точки измерения
     *
     * @param objectId            айди объекта
     * @param ventilationSystemId айди вент. системы
     * @param pointId             айди точки измерения
     * @return страницу с списком точек измерений
     */
    @GetMapping("/objects/{objectId}/vents/{ventilationSystemId}/points/delete/{pointId}")
    public String deletePoint(@PathVariable("objectId") Long objectId,
                              @PathVariable("ventilationSystemId") Long ventilationSystemId,
                              @PathVariable Long pointId) {
        pointService.deleteById(pointId);
        return "redirect:/commissioning/objects/" + objectId + "/vents/" + ventilationSystemId + "/points";
    }

    /**
     * Форсированое удаление точки измерения и всех ее измерений
     *
     * @param objectId            айди объекта
     * @param ventilationSystemId айди вент. системы
     * @param pointId             айди точки измерения
     * @return страницу со списком точек измерения
     */
    @GetMapping("/objects/{objectId}/vents/{ventilationSystemId}/points/forcedelete/{pointId}")
    public String forceDeletePoint(@PathVariable("objectId") Long objectId,
                                   @PathVariable("ventilationSystemId") Long ventilationSystemId,
                                   @PathVariable Long pointId) {
        Point point = pointService.findById(pointId).get();
        for (Measurements meas : point.getListAirFlowRate()) {
            measurementsService.deleteById(meas.getMeasurementsId());
        }
        pointService.deleteById(pointId);
        return "redirect:/commissioning/objects/" + objectId + "/vents/" + ventilationSystemId + "/points";
    }

    /**
     * Изменение точки измерения
     *
     * @param objectId            айди объекта
     * @param ventilationSystemId айди вент. системы
     * @param pointId             айди точки измерения
     * @param model               модель
     * @return страницу с изменением точки измерения
     */
    @GetMapping("/objects/{objectId}/vents/{ventilationSystemId}/points/update/{pointId}")
    public String updatePoint(@PathVariable("objectId") Long objectId,
                              @PathVariable("ventilationSystemId") Long ventilationSystemId,
                              @PathVariable("pointId") Long pointId,
                              Model model) {
        PlaceOfWork placeOfWork = placeOfWorkService.findById(objectId).get();
        VentilationSystem ventilationSystem = ventilationSystemService.findById(ventilationSystemId).get();
        Point pointToUpdate = pointService.findById(pointId).get();
        model.addAttribute("update_point", dtoMapper.toPointDto(pointToUpdate));
        model.addAttribute("vent", dtoMapper.toVentilationSystemDto(ventilationSystem));
        model.addAttribute("object", dtoMapper.toPlaceOfWorkDto(placeOfWork));
        model.addAttribute("circular", TypeOfHole.CIRCULAR);
        model.addAttribute("rectangular", TypeOfHole.RECTANGULAR);
        model.addAttribute("type_meas_inside", TypeMeasuring.INSIDE_THE_DUCT);
        model.addAttribute("type_meas_on", TypeMeasuring.ON_THE_GRATE);
        return "point_update_page";
    }

    /**
     * Изменение точки измерения
     *
     * @param objectId            айди объекта
     * @param ventilationSystemId айди вент. системы
     * @param updatedPoint        измененная точка измерения
     * @return возвращает на страницу с точками измерения
     */
    @PostMapping("/objects/{objectId}/vents/{ventilationSystemId}/points/update")
    public String updatePoint(@PathVariable("objectId") Long objectId,
                              @PathVariable("ventilationSystemId") Long ventilationSystemId,
                              @ModelAttribute("update_point") @Validated PointDto updatedPoint,
                              BindingResult br, Model model) {
        VentilationSystem ventilationSystem = ventilationSystemService.findById(ventilationSystemId).get();
        PlaceOfWork placeOfWork = placeOfWorkService.findById(objectId).get();
        if (br.hasErrors()){
            model.addAttribute("vent", dtoMapper.toVentilationSystemDto(ventilationSystem));
            model.addAttribute("object", dtoMapper.toPlaceOfWorkDto(placeOfWork));
            model.addAttribute("circular", TypeOfHole.CIRCULAR);
            model.addAttribute("rectangular", TypeOfHole.RECTANGULAR);
            model.addAttribute("type_meas_inside", TypeMeasuring.INSIDE_THE_DUCT);
            model.addAttribute("type_meas_on", TypeMeasuring.ON_THE_GRATE);
            return "point_update_page";
        }
        updatedPoint.setVentilationSystem(ventilationSystem);
        pointService.updatePoint(dtoMapper.toPoint(updatedPoint));
        return "redirect:/commissioning/objects/" + objectId + "/vents/" + ventilationSystemId + "/points";
    }

    /**
     * Просмотр одной точки измерения целиком
     *
     * @param objectId            айди объекта
     * @param ventilationSystemId айди вент. системы
     * @param pointId             айди точки измерения
     * @param model               модель
     * @return страницу с просмотром одной точки измерения
     */
    @GetMapping("/objects/{objectId}/vents/{ventilationSystemId}/points/{pointId}")
    public String getOnePoint(@PathVariable("objectId") Long objectId,
                              @PathVariable("ventilationSystemId") Long ventilationSystemId,
                              @PathVariable Long pointId,
                              Model model) {
        PlaceOfWork placeOfWork = placeOfWorkService.findById(objectId).get();
        VentilationSystem ventilationSystem = ventilationSystemService.findById(ventilationSystemId).get();
        Point point = pointService.findById(pointId).get();
        model.addAttribute("vent", dtoMapper.toVentilationSystemDto(ventilationSystem));
        model.addAttribute("object", dtoMapper.toPlaceOfWorkDto(placeOfWork));
        model.addAttribute("point", dtoMapper.toPointDto(point));
        model.addAttribute("circular", TypeOfHole.CIRCULAR);
        model.addAttribute("rectangular", TypeOfHole.RECTANGULAR);
        model.addAttribute("type_meas_inside", TypeMeasuring.INSIDE_THE_DUCT);
        model.addAttribute("type_meas_on", TypeMeasuring.ON_THE_GRATE);
        return "point_page";
    }

    //endregion

    //region Код для типа данных Measurments

    /**
     * Добавление измерения
     *
     * @param objectId            айди объекта
     * @param ventilationSystemId айди вент. системы
     * @param pointId             айди точки измерения
     * @param model               модель
     * @return страницу с добавлением измерения
     */
    @GetMapping("/objects/{objectId}/vents/{ventilationSystemId}/points/{pointId}/add")
    public String addMeasure(@PathVariable("objectId") Long objectId,
                             @PathVariable("ventilationSystemId") Long ventilationSystemId,
                             @PathVariable Long pointId,
                             Model model) {
        PlaceOfWork placeOfWork = placeOfWorkService.findById(objectId).get();
        VentilationSystem ventilationSystem = ventilationSystemService.findById(ventilationSystemId).get();
        Point point = pointService.findById(pointId).get();
        model.addAttribute("vent", dtoMapper.toVentilationSystemDto(ventilationSystem));
        model.addAttribute("object", dtoMapper.toPlaceOfWorkDto(placeOfWork));
        model.addAttribute("point", dtoMapper.toPointDto(point));
        model.addAttribute("new_meas", new MeasurementsDto());
        return "measure_add_page";
    }

    /**
     * Добавление измерения
     *
     * @param objectId            айди объекта
     * @param ventilationSystemId айди вент. системы
     * @param pointId             айди точки измерения
     * @param newMeasure          новое измерение
     * @return возвращает на страницу с точками измерения
     */
    @PostMapping("/objects/{objectId}/vents/{ventilationSystemId}/points/{pointId}/add")
    public String addMeasure(@PathVariable Long objectId, @PathVariable Long ventilationSystemId,
                             @PathVariable Long pointId, @ModelAttribute("new_meas") @Validated MeasurementsDto newMeasure,
                             BindingResult br, Model model) {
        PlaceOfWork mainObject = placeOfWorkService.findById(objectId).get();
        VentilationSystem mainVent = ventilationSystemService.findById(ventilationSystemId).get();
        Point mainPoint = pointService.findById(pointId).get();
        if (br.hasErrors()){
            model.addAttribute("vent", dtoMapper.toVentilationSystemDto(mainVent));
            model.addAttribute("object", dtoMapper.toPlaceOfWorkDto(mainObject));
            model.addAttribute("point", dtoMapper.toPointDto(mainPoint));
            return "measure_add_page";
        }
        Double newAirFlowRate = Double.valueOf(newMeasure.getValueOfMeasure());
        Double newAirVolume = (double) Math.round(newAirFlowRate * 3600 * mainPoint.getCrossSectionalArea() * 10.0) / 10;
        mainPoint.setCurrentAirFlowRate(newAirFlowRate);
        mainPoint.setCurrentAirVolume(newAirVolume);
        newMeasure.setPointId(mainPoint);
        mainPoint.getListAirFlowRate().add(dtoMapper.toMeasurements(newMeasure));
        Double discrepancy = (double) -(100 - Math.round(newAirFlowRate / mainPoint.getAirFlowRate() * 100));
        mainPoint.setDiscrepancy(discrepancy);
        measurementsService.save(dtoMapper.toMeasurements(newMeasure));
        pointService.updatePoint(mainPoint);
        ventilationSystemService.updateVentilationSystem(mainVent);
        placeOfWorkService.updatePlaceOfWork(mainObject);
        return "redirect:/commissioning/objects/" + objectId + "/vents/" + ventilationSystemId + "/points";
    }
    //endregion

}
