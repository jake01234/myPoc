package com.jake.crud.app.controller;

import com.jake.crud.app.entity.Person;
import com.jake.crud.app.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PersonController {

    @Autowired
    private PersonService service;


    @PostMapping("/addPerson")
    public String addPerson(@Valid Person person, BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            return "person_add_form";
        }

        try{
            service.savePerson(person);
        }
        catch(Exception e){
            model.addAttribute("error",e.getMessage());
            return "person_add_form";
        }

        return "redirect:/people";
    }

    @GetMapping("/person_add_form")
    public String addPerson(@Valid Person person,Model model) throws Exception {
        return "person_add_form";
    }


    @GetMapping("/people")
    public String findAllPeople(Model model) {
        try {
            List<Person> people = new ArrayList<Person>();
            service.getPeople().forEach(people::add);
            model.addAttribute("people", people);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "people";
    }

    @GetMapping("/personById/{id}")
    public String findPersonById(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) throws Exception {
        try {
            Person person = service.getPersonById(id);
            model.addAttribute("person", person);

            return "person_edit_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/people";
        }
    }

    @PostMapping("/update")
    public String savePerson(Person person, Model model,RedirectAttributes redirectAttributes) {
        try {
            service.updatePerson(person);

            redirectAttributes.addFlashAttribute("message", "The Person has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
            return "person_edit_form";
        }

        return "redirect:/people";
    }

    @GetMapping("/delete/{id}")
    public String deletePerson(@PathVariable int id,Model model, RedirectAttributes redirectAttributes) throws Exception {
        try {
            service.deletePerson(id);

            redirectAttributes.addFlashAttribute("message", "The Person with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/people";
    }
}
