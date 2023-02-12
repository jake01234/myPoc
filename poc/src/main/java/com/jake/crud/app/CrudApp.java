package com.jake.crud.app;

import com.jake.crud.app.entity.ERole;
import com.jake.crud.app.entity.Role;
import com.jake.crud.app.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Optional;

@SpringBootApplication
public class CrudApp {

	@Autowired
	RoleRepository roleRepository;

	@PostConstruct
	public void runAfterObjectCreated() {
		Optional<Role> role= roleRepository.findByName(ERole.ROLE_USER);

		if(!role.isPresent()){
			roleRepository.save(new Role(ERole.ROLE_USER));
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(CrudApp.class, args);
	}

}
