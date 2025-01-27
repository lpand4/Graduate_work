package ru.pugovkinv.commissioningofventilationsystems.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pugovkinv.commissioningofventilationsystems.domain.mainModels.PlaceOfWork;
import ru.pugovkinv.commissioningofventilationsystems.services.PlaceOfWorkService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class PlaceOfWorkController {
    private final PlaceOfWorkService service;

    @GetMapping("/home")
    public String home(){
        return "home";
    }


    @GetMapping("/commisioning")
    public String getAllObjects(Model model){
        List<PlaceOfWork> placeOfWorkList = service.findAll();
        System.out.println(placeOfWorkList);
        model.addAttribute("objects",placeOfWorkList);
        return "commissioning";
    }

}
