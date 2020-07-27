package dev.emiliofranco.springjwtstudies.controller;

import dev.emiliofranco.springjwtstudies.model.AppUser;
import dev.emiliofranco.springjwtstudies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    UserRepository userRepository;

    @PutMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addUser(@RequestBody Map map){
        String userName = (String) map.get("userName");
        String password = (String) map.get("password");
        return userRepository.addUser(userName, password);
    }

    @GetMapping("/user")
    public String getUser(@RequestBody Map map){
        String userName = (String) map.get("userName");
        AppUser user= userRepository.findByUserName(userName);
        return user != null? "user found" : " no such user";
    }
}
