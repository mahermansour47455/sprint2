package com.maher.section345.controllers;

import com.maher.section345.entities.Equipe;
import com.maher.section345.service.EquipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class EquipeController {

    @Autowired
    EquipeService equipeService;
    @RequestMapping("/showCreate")
    public String showCreate(ModelMap modelMap)
    {
        modelMap.addAttribute("equipe", new Equipe());
        modelMap.addAttribute("mode", "new");

        return "formEquipe";
    }


    @RequestMapping("/saveEquipe")
    public String saveEquipe(@Valid Equipe equipe,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) return "formEquipe";
        equipeService.saveEquipe(equipe);

        return "formEquipe";
    }


    @RequestMapping("/ListeEquipe")
    public String ListeEquipe(ModelMap modelMap,
                                @RequestParam (name="page",defaultValue = "0") int page,
                                @RequestParam (name="size", defaultValue = "2") int size)
    {
        Page <Equipe> equipes = equipeService.getAllEquipesParPage(page, size);
        modelMap.addAttribute("equipes", equipes);
        modelMap.addAttribute("pages", new int[equipes.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "ListeEquipe";


    }
    @RequestMapping("/supprimerEquipe")
    public String supprimerEquipe(@RequestParam("id") Long id,
                                ModelMap modelMap,
                                @RequestParam (name="page",defaultValue = "0") int page,
                                @RequestParam (name="size", defaultValue = "2") int size)
    {
        equipeService.deleteEquipeById(id);
        Page <Equipe> equipes = equipeService.getAllEquipesParPage(page, size);
        modelMap.addAttribute("equipes", equipes);
        modelMap.addAttribute("pages", new int[equipes.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "ListeEquipe";
    }
    @RequestMapping("/modifierEquipe")
    public String modifierEquipe(@RequestParam("id") Long id,
                                  ModelMap modelMap)
    {
        Equipe e = equipeService.getEquipe(id);
        modelMap.addAttribute("equipe", e);
        modelMap.addAttribute("mode", "edit");

        return "formEquipe";
    }
    @RequestMapping("/updateEquipe")
    public String updateEquipe(@ModelAttribute("equipe") Equipe equipe,
                               @RequestParam("date") String date,
                               @RequestParam("idEquipe") Long id,
                               @RequestParam (name="page",defaultValue = "0") int page,
                               @RequestParam (name="size", defaultValue = "2") int size,
                               ModelMap modelMap) throws
            ParseException
    {
//conversion de la date
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateCreation = dateformat.parse(String.valueOf(date));
        equipe.setDateFound(dateCreation);
        equipeService.updateEquipe(equipe);
        Page <Equipe> equipes = equipeService.getAllEquipesParPage(page, size);
        modelMap.addAttribute("equipes", equipes);
        modelMap.addAttribute("pages", new int[equipes.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "ListeEquipe";
    }


}
