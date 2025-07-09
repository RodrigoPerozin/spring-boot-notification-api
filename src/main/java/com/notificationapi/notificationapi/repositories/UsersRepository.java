package com.notificationapi.notificationapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.notificationapi.notificationapi.model.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long>{

    Optional<User> findByEmailAndPassword(String email, String password);
    
}
