package ru.pugovkinv.commissioningofventilationsystems.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.PlaceOfWork;
import ru.pugovkinv.commissioningofventilationsystems.services.PlaceOfWorkService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/commissioning")
public class CommissioningController {
    private final PlaceOfWorkService placeOfWorkService;

    @GetMapping("/")
    public String home(){
        return "home";
    }
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
    @GetMapping("/objects/update/{objectId}")
    public String updateObject(@PathVariable("objectId") Long id, Model model){
        PlaceOfWork placeOfWorkToUpdate = placeOfWorkService.findById(id).get();
        model.addAttribute("update_object", placeOfWorkToUpdate);
        return "object_update_page";
    }
    @PostMapping("/objects/update")
    public String updateObject(@ModelAttribute("update_object") PlaceOfWork updatedObject){
        placeOfWorkService.updatePlaceOfWork(updatedObject);
        return "redirect:/commissioning/objects";
    }




}
