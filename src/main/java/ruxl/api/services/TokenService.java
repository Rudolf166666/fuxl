package ruxl.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruxl.api.exceptions.ApiException;
import ruxl.api.models.Token;
import ruxl.api.models.User;
import ruxl.api.payload.response.RefreshResponse;
import ruxl.api.repositories.JWTRepo;
import ruxl.api.repositories.TokenRepo;
import ruxl.api.repositories.UserRepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
public class TokenService {
    @Autowired
    TokenRepo tokenRepository;
    @Autowired
    JWTRepo jwtRepository;

    @Autowired
    UserRepo userRepository;
    @PersistenceContext
    private EntityManager em;
    public void updateRefreshToken(String refresh_token, User newUser){

    }

    @Transactional
    public ResponseEntity<RefreshResponse> refresh(String refresh_token){
        boolean isValid=jwtRepository.validateJwtToken(refresh_token);
        if(!isValid)throw ApiException.generate403Exception("Refresh token has been expired");
        Optional<Token> foundedToken=tokenRepository.findByToken(refresh_token);
        Token token=foundedToken.get();
        String email=jwtRepository.getUserEmailByToken(refresh_token);
        Optional<User> user= userRepository.findByEmail(email);
        if(user.isEmpty())throw ApiException.generate500Exception("Refresh token is not valid");
        String access_token=jwtRepository.generateAccessToken(user.get());
        String new_refresh_token=jwtRepository.generateRefreshToken(user.get());
        token.setToken(new_refresh_token);
        em.persist(token);
        return  ResponseEntity.ok().body(new RefreshResponse(new_refresh_token,access_token));

    }

}
