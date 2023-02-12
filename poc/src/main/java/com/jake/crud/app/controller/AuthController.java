package com.jake.crud.app.controller;




import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.jake.crud.app.entity.Person;
import com.jake.crud.app.payload.request.LoginRequest;
import com.jake.crud.app.payload.request.SignupRequest;
import com.jake.crud.app.payload.response.MessageResponse;
import com.jake.crud.app.repository.PersonRepository;
import com.jake.crud.app.repository.RoleRepository;
import com.jake.crud.app.service.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@Controller

public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PersonRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder encoder;


    @GetMapping("/")
    public String rootPage() {
        return "redirect:/people";
    }

    @GetMapping("login")
    public String loginPage(LoginRequest loginRequest) {
        return "login";
    }

    @PostMapping("/api/auth/signin")
    public String authenticateUser(@Valid LoginRequest loginRequest, BindingResult result, Model model, HttpServletRequest request) {

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwtToken = jwtUtils.generateJwtToken(authentication);

            request.getSession().setAttribute("token", jwtToken);

            return "redirect:/people";
        }
        catch (Exception e){
           model.addAttribute("error",e.getMessage());
           return "login";
        }
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        // Create new user's account
        Person user = new Person();
        user.setAge(signUpRequest.getAge());
        user.setName(signUpRequest.getName());
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setSsn(signUpRequest.getSsn());
        user.setCity(signUpRequest.getCity());
        user.setAddress(signUpRequest.getAddress());
        user.setPhone(signUpRequest.getPhone());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/signout")
    public String logoutUser(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }
}