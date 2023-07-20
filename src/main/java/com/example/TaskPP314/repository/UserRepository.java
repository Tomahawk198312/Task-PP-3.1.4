package com.example.TaskPP314.repository;

import com.example.TaskPP314.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "User.roles")
    User findByName(String name);

}