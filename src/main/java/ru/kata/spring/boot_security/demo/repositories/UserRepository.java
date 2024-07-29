package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

@Repository
public interface UserRepository  extends JpaRepository <User, Integer> {
    @Query("SELECT u FROM User u WHERE u.name = :username")
    User findByUsername(@Param("username") String username);
}
