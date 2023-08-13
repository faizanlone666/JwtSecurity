package com.security.controller;

import com.security.entity.ApplicationUser;
import com.security.model.AuthenticationRequest;
import com.security.model.LoginResponseDto;
import com.security.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody AuthenticationRequest request){
        return authenticationService.registerUser(request.getUserName(), request.getPassword());
    }

    @PostMapping("/login")
    public LoginResponseDto loginUserName(@RequestBody AuthenticationRequest request) throws Exception {
        return authenticationService.loginUser(request.getUserName(),request.getPassword());
    }
}
