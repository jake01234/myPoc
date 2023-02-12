package com.jake.crud.app.service;

import com.jake.crud.app.entity.ERole;
import com.jake.crud.app.entity.Person;
import com.jake.crud.app.entity.Role;
import com.jake.crud.app.repository.PersonRepository;
import com.jake.crud.app.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonService {
    @Autowired
    private PersonRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    public Person savePerson(Person person) throws Exception {
        if (repository.existsByUsername(person.getUsername())) {
           throw new Exception("Error: Username is already taken!");
        }

        if (repository.existsByEmail(person.getEmail())) {
            throw new Exception("Error: Email is already in use!");
        }
        person.setPassword(encoder.encode(person.getPassword()));
        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        roles.add(userRole);
        person.setRoles(roles);
        return repository.save(person);
    }

    public List<Person> savePeople(List<Person> people) {
        return repository.saveAll(people);
    }

    public List<Person> getPeople() {
        return repository.findAll();
    }

    public Person getPersonById(int id) throws Exception {
        Optional<Person> personExists = repository.findById(id);
        if(!personExists.isPresent()){
            throw new Exception("Person with this Id does not exists");
        }
        return repository.findById(id).orElse(null);
    }

    public Person getPersonByName(String name) throws Exception {
        Optional<Person> personExists = repository.findByName(name);
        if(!personExists.isPresent()){
            throw new Exception("Person with this name does not exists");
        }
        return repository.findByName(name).orElse(null);
    }

    public String deletePerson(int id) throws Exception {
        Optional<Person> personExists = repository.findById(id);
        if(!personExists.isPresent()){
            throw new Exception("Person with this Id does not exists");
        }
        repository.deleteById(id);
        return "Person removed !! " + id;
    }

    public Person updatePerson(Person person) throws Exception {
        Optional<Person> personExists = repository.findById(person.getId());
        if(!personExists.isPresent()){
            throw new Exception("Person with this Id does not exists");
        }

        Person existingPerson= personExists.get();
        existingPerson.setName(person.getName());
        existingPerson.setAge(person.getAge());
        existingPerson.setSsn(person.getSsn());
        existingPerson.setPhone(person.getPhone());
        existingPerson.setAddress(person.getAddress());
        existingPerson.setCity(person.getCity());
        return repository.save(existingPerson);
    }


}
