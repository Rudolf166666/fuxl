package ruxl.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ruxl.api.models.User;
import ruxl.api.payload.request.LoginRequest;
import ruxl.api.payload.request.RefreshRequest;
import ruxl.api.payload.response.RefreshResponse;
import ruxl.api.payload.response.RegistrationResponse;
import ruxl.api.services.TokenService;
import ruxl.api.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    @GetMapping
    public String test(){
        return ("<h1>Hello world</h1>");
    }

    @PostMapping(path = "/registration",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationResponse> registration(@Valid @RequestBody User newUser){
        return userService.registration(newUser);
    }

    @PostMapping(path = "/login",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationResponse> login(@Valid @RequestBody LoginRequest body){
        return userService.login(body);
    }

    @GetMapping(path="/details")
    public ResponseEntity<User> getAuthorizedUser(HttpServletRequest request){
        String access_token=request.getHeader("Authorization").split(" ")[1];
        return userService.getUserDetails(access_token);
    }

    @PostMapping(path = "/refresh",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RefreshResponse> refresh(@Valid @RequestBody RefreshRequest refreshRequest){
        return tokenService.refresh(refreshRequest.getRefresh_token());
    }
}
