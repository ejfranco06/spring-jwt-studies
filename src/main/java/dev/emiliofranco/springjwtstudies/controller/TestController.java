package dev.emiliofranco.springjwtstudies.controller;

import dev.emiliofranco.springjwtstudies.exception.UserAuthException;
import dev.emiliofranco.springjwtstudies.model.AppUser;
import dev.emiliofranco.springjwtstudies.model.AuthenticationRequest;
import dev.emiliofranco.springjwtstudies.model.AuthenticationResponse;
import dev.emiliofranco.springjwtstudies.repository.UserRepository;
import dev.emiliofranco.springjwtstudies.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.PasswordAuthentication;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping( "/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        UserDetails userDetails;
        try {
            userDetails = (UserDetails) authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword())
            ).getPrincipal();
        } catch (Exception e) {
            throw new UserAuthException("Invalid username or password");
        }
        final String jwtToken = jwtService.generateToken(userDetails);
        return new ResponseEntity(new AuthenticationResponse(jwtToken), HttpStatus.OK);
    }

    @PutMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addUser(@RequestBody Map map) {
        String userName = (String) map.get("userName");
        String password = (String) map.get("password");
        return userRepository.addUser(userName, password);
    }


    @GetMapping("/hello")
    public String getHello(){
        return "hello world";
    }

    @GetMapping("/user")
    public String getUser(@RequestBody Map map) {
        String userName = (String) map.get("userName");
        AppUser user= userRepository.findByUserName(userName).orElse(null );
        return userName != null ? "user found " + userName : " no such user";
    }
}
