package com.Management.repository;

import com.Management.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findUserByToken(String token);

    @Query(value = "select t from Token t inner join User u on t.user.id = u.userId where u.userId = :userId and (t.expired = false or t.revoked = false )")
    List<Token> findTokenByUserId(@Param("userId") Integer userId);
}
