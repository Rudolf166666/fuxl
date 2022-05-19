package ruxl.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ruxl.api.exceptions.ApiException;
import ruxl.api.models.Token;
import ruxl.api.models.User;
import ruxl.api.payload.request.LoginRequest;
import ruxl.api.payload.response.RefreshResponse;
import ruxl.api.payload.response.RegistrationResponse;
import ruxl.api.repositories.JWTRepo;
import ruxl.api.repositories.TokenRepo;
import ruxl.api.repositories.UserRepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.namespace.QName;
import java.util.List;
import java.util.Optional;

@Service
public class UserService  {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private TokenRepo tokenRepository;
    @Autowired
    private JWTRepo jwtRepository;
    @PersistenceContext
    private EntityManager em;

    public ResponseEntity<RegistrationResponse> registration(User user){
        Optional<User> foundedUser=userRepository.findByEmail(user.getEmail());
        if(foundedUser.isPresent())throw ApiException.generate409Exception("User with this email already exist");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser=userRepository.save(user);
        String access_token=jwtRepository.generateAccessToken(newUser);
        String refresh_token=jwtRepository.generateRefreshToken(newUser);
        tokenRepository.save(new Token(refresh_token,newUser));
        return ResponseEntity.ok().body(new RegistrationResponse(access_token,refresh_token,newUser));
    }

    public ResponseEntity<RegistrationResponse> login(LoginRequest body){
        Optional<User> foundedUser=userRepository.findByEmail(body.getEmail());
        if(foundedUser.isEmpty())throw ApiException.generate403Exception("Cannot find user with this email");
        User user=foundedUser.get();
        System.out.println();
        if(!passwordEncoder.matches(body.getPassword(), user.getPassword()))
            throw ApiException.generate403Exception("You entered wrong password");
        String access_token=jwtRepository.generateAccessToken(user);
        String refresh_token=jwtRepository.generateRefreshToken(user);
        Optional<Token> token=tokenRepository.findByUser(user.getId());
        if(token.isPresent()){
            token.get().setToken(refresh_token);
        }
        return ResponseEntity.ok().body(new RegistrationResponse(access_token,refresh_token,user));
    }

    public ResponseEntity<User>getUserDetails(String access_token){
        System.out.println("is here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Long id=jwtRepository.getUserIdByToken(access_token);
        Optional<User>user=userRepository.findById(id);

        return ResponseEntity.ok().body(user.get());
    }
}
