package com.jake.crud.app.controller;

import com.jake.crud.app.entity.Person;
import com.jake.crud.app.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService service;

    @PostMapping("/addPerson")
    public Person addProduct(@RequestBody Person person) {
        return service.savePerson(person);
    }

    @PostMapping("/addPeople")
    public List<Person> addPeople(@RequestBody List<Person> people) {
        return service.savePeople(people);
    }

    @GetMapping("/people")
    public List<Person> findAllPeople() {
        return service.getPeople();
    }

    @GetMapping("/personById/{id}")
    public Person findPersonById(@PathVariable int id) {
        return service.getPersonById(id);
    }

    @GetMapping("/person/{name}")
    public Person findPersonByName(@PathVariable String name) {
        return service.getPersonByName(name);
    }

    @PutMapping("/update")
    public Person updatePerson(@RequestBody Person person) {
        return service.updatePerson(person);
    }

    @DeleteMapping("/delete/{id}")
    public String deletePerson(@PathVariable int id) {
        return service.deletePerson(id);
    }
}
