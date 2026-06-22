package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.CustomerRegisterDto;
import com.InsuranceSystem.dto.LoginResponseDto;
import com.InsuranceSystem.dto.LoginRequestDto;
import com.InsuranceSystem.dto.TokenDto;
import com.InsuranceSystem.model.Customer;
import com.InsuranceSystem.model.User;
import com.InsuranceSystem.service.UserService;
import com.InsuranceSystem.utility.JwtUtility;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin (origins = "http://localhost:5173")
public class AuthController {
    private final UserService userService;
    private final JwtUtility jwtUtility;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/customer/register")
    public void registerCustomer(@Valid @RequestBody CustomerRegisterDto dto) {
        userService.registerCustomer(dto);
    }



    /*
    .requestMatchers(HttpMethod.GET, "/api/auth/login").authenticated()

    This ensures that , if user request comes to this login() method at line
    19.. then Spring already has checked username/password and they are right
    and i can ask for username from spring
    * */
    @GetMapping("/login")
    public TokenDto Login(Principal principal){
        String username = principal.getName();
        String token = jwtUtility.generateToken(username);
        return new TokenDto(username,token);
    }

    // this is for later
    @GetMapping("/user-details")
    public LoginResponseDto getUserDetails(Principal principal){
        User user = (User)userService.loadUserByUsername(principal.getName());
        return new LoginResponseDto(
                user.getId(),
                user.getUsername(),
                user.getRole().toString()
        );
    }
}
