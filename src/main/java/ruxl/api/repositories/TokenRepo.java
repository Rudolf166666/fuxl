package ruxl.api.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ruxl.api.models.Token;

import java.util.Optional;

public interface TokenRepo extends CrudRepository<Token,Long> {
    @Query("select t from Token t where t.user.id=:id")
    Optional<Token>findByUser(@Param("id") Long id);
    Optional<Token>findByToken(String token);
}
