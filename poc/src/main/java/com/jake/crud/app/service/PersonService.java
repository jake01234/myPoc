package com.jake.crud.app.service;

import com.jake.crud.app.entity.Person;
import com.jake.crud.app.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository repository;

    public Person savePerson(Person person) {
        return repository.save(person);
    }

    public List<Person> savePeople(List<Person> people) {
        return repository.saveAll(people);
    }

    public List<Person> getPeople() {
        return repository.findAll();
    }

    public Person getPersonById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Person getPersonByName(String name) {
        return repository.findByName(name);
    }

    public String deletePerson(int id) {
        repository.deleteById(id);
        return "person removed !! " + id;
    }

    public Person updatePerson(Person person) {
        Person existingPerson = repository.findById(person.getId()).orElse(null);
        existingPerson.setName(person.getName());
        existingPerson.setAge(person.getAge());
        existingPerson.setSsn(person.getSsn());
        return repository.save(existingPerson);
    }


}
