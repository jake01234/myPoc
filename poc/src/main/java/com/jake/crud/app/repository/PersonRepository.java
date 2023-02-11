package com.jake.crud.app.repository;

import com.jake.crud.app.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Integer> {
    Person findByName(String name);
}

