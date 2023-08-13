package com.security.service;

import com.security.entity.ApplicationUser;
import com.security.entity.Role;
import com.security.model.LoginResponseDto;
import com.security.repository.RoleRepository;
import com.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public ApplicationUser registerUser(String userName, String password){
        Role userRole = roleRepository.findByAuthority("USER").get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);
        return userRepository.save(
                ApplicationUser.builder().userName(userName).password(passwordEncoder.encode(password)).authorities(authorities).build()
        );
    }

    public LoginResponseDto loginUser(String userName, String password)throws Exception{
        String token;
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userName, password)
            );
             token = tokenService.generateJwt(auth);
        }catch (Exception e){
            throw e;

        }
        return new LoginResponseDto(userRepository.findByUserName(userName).get(),token);
    }
}
