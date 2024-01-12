package com.example.bugwarshealerbackend.jpa;

import com.example.bugwarshealerbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    
    //retrieved the user from username
    @Query(value = "SELECT u FROM User u where u.username = ?1")
    User findByUsername(String username);
}
