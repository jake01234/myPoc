package com.jake.crud.app.repository;

import com.jake.crud.app.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person,Integer> {
    Optional<Person> findByName(String name);

    Optional<Person> findByUsername(String userName);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}

