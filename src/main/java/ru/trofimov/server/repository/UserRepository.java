package ru.trofimov.server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.trofimov.server.domain.UserEntity;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, String> {

    //added for demonstration only. CrudRepository already has findById method
    @Query("select user from UserEntity user where user.id = :id")
    Optional<UserEntity> findUserById(@Param("id") String userId);

    Optional<UserEntity> findUserByLogin(String userId);
}
