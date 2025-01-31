package ru.pugovkinv.commissioningofventilationsystems.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.PlaceOfWork;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.VentilationSystem;
import ru.pugovkinv.commissioningofventilationsystems.services.PlaceOfWorkService;
import ru.pugovkinv.commissioningofventilationsystems.services.VentilationSystemService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/commissioning")
public class CommissioningController {
    private final PlaceOfWorkService placeOfWorkService;
    private final VentilationSystemService ventilationSystemService;

    @GetMapping("/")
    public String home(){
        return "home";
    }
    //region Код под тип PlaceOfWork
    /**
     * Вывод всех доступных объектов наладки
     * @param model модель
     * @return страница с списком объектов наладки
     */
    @GetMapping("/objects")
    public String getAllObjects(Model model){
        List<PlaceOfWork> placeOfWorkList = placeOfWorkService.findAll();
        model.addAttribute("objects",placeOfWorkList);
        return "object_main_page";
    }
    /**
     * Страница добавления нового объекта наладки
     * @param model модель
     * @return страница создания нового объекта
     */
    @GetMapping("/objects/add")
    public String addObject(Model model){
        model.addAttribute("new_object", new PlaceOfWork());
        return "object_add_page";
    }
    /**
     * Отправление нового объекта в БД
     * @param newObject новый объект
     * @return возвращает на страницу с списком объектов
     */
    @PostMapping("/objects/add")
    public String addObject(@ModelAttribute("new_object") PlaceOfWork newObject){
        placeOfWorkService.save(newObject);
        return "redirect:/commissioning/objects";
    }
    /**
     * Удаление объекта наладки по кнопке
     * @param id айди нужного объекта
     * @return возвращает на страницу с списком объектов
     */
    @GetMapping("/objects/delete/{objectId}")
    public String deleteObject(@PathVariable("objectId") Long id){
        placeOfWorkService.deleteById(id);
        return "redirect:/commissioning/objects";
    }
    /**
     * Изменение данных объекта наладки
     * @param id айди необходимого объекта
     * @param model модель
     * @return страница изменения объекта наладки
     */
    @GetMapping("/objects/update/{objectId}")
    public String updateObject(@PathVariable("objectId") Long id, Model model){
        PlaceOfWork placeOfWorkToUpdate = placeOfWorkService.findById(id).get();
        model.addAttribute("update_object", placeOfWorkToUpdate);
        return "object_update_page";
    }
    /**
     * Отправка измененного объекта
     * @param updatedObject измененный объект
     * @return возвращает на страницу с списком объектов
     */
    @PostMapping("/objects/update")
    public String updateObject(@ModelAttribute("update_object") PlaceOfWork updatedObject){
        placeOfWorkService.updatePlaceOfWork(updatedObject);
        return "redirect:/commissioning/objects";
    }
    //endregion
    // region Код под тип VentilationSystem
    /**
     * Получение всех вент. систем объекта
     * @param objectId айди объекта
     * @param model модель
     * @return возвращает страницу с списков вент. систем
     */
    @GetMapping("/objects/{objectId}/vents")
    public String getAllVents(@PathVariable Long objectId, Model model){
        PlaceOfWork placeOfWork = placeOfWorkService.findById(objectId).get();
        List<VentilationSystem> ventilationSystemList = ventilationSystemService.findAll(placeOfWork);
        model.addAttribute("vents",ventilationSystemList);
        model.addAttribute("object", placeOfWork);
        return "ventsystem_main_page";
    }
    /**
     * Добавление новой вент. системы
     * @param objectId айди объекта
     * @param model модель
     * @return возвращает страницу с добавлением новой вент. системы
     */
    @GetMapping("/objects/{objectId}/vents/add")
    public String addVent(@PathVariable Long objectId, Model model){
        PlaceOfWork placeOfWork = placeOfWorkService.findById(objectId).get();
        model.addAttribute("new_vent", new VentilationSystem());
        model.addAttribute("object", placeOfWork);
        return "ventsystem_add_page";
    }
    /**
     * Отправка новой вент. системы в БД
     * @param objectId айди объекта
     * @param newVent новая вент. система
     * @return возвращает на страницу со списком вент. систем
     */
    @PostMapping("/objects/{objectId}/vents/add")
    public String addVent(@PathVariable Long objectId, @ModelAttribute("new_vent") VentilationSystem newVent){
        PlaceOfWork mainObject = placeOfWorkService.findById(objectId).get();
        newVent.setPlaceOfWork(mainObject);
        mainObject.getVentilationSystems().add(newVent);
        placeOfWorkService.updatePlaceOfWork(mainObject);
        ventilationSystemService.save(newVent);
        return "redirect:/commissioning/objects/"+ objectId+ "/vents";
    }
    /**
     * Удаление вент. системы
     * @param objectId айди объекта
     * @param ventilationSystemId айди вент. системы
     * @return возвращает на страницу с списком вент. систем
     */
    @GetMapping("/objects/{objectId}/vents/delete/{ventilationSystemId}")
    public String deleteVent(@PathVariable("objectId") Long objectId, @PathVariable("ventilationSystemId") Long ventilationSystemId){
        ventilationSystemService.deleteById(ventilationSystemId);
        return "redirect:/commissioning/objects/"+ objectId + "/vents";
    }
    /**
     *  Обновление вент. системы
     * @param objectId айди объекта
     * @param ventilationSystemId айди вент. системы
     * @param model модель
     * @return возвращает страницу с изменением вент. системы
     */
    @GetMapping("/objects/{objectId}/vents/update/{ventilationSystemId}")
    public String updateVent(@PathVariable("objectId") Long objectId, @PathVariable("ventilationSystemId") Long ventilationSystemId, Model model){
        PlaceOfWork placeOfWork = placeOfWorkService.findById(objectId).get();
        VentilationSystem ventilationSystemToUpdate = ventilationSystemService.findById(ventilationSystemId).get();
        model.addAttribute("update_vent", ventilationSystemToUpdate);
        model.addAttribute("object", placeOfWork);
        return "ventsystem_update_page";
    }
    /**
     * Отправка обновленной вент. системы
     * @param objectId айди объекта
     * @param updatedVent обновленная вент. система
     * @return возвращает на страницу со списком вент. систем
     */
    @PostMapping("/objects/{objectId}/vents/update")
    public String updateVent(@PathVariable("objectId") Long objectId, @ModelAttribute("update_vent") VentilationSystem updatedVent){
        PlaceOfWork placeOfWork = placeOfWorkService.findById(objectId).get();
        updatedVent.setPlaceOfWork(placeOfWork);
        ventilationSystemService.updateVentilationSystem(updatedVent);
        return "redirect:/commissioning/objects/"+ objectId + "/vents";
    }
    //endregion



}
