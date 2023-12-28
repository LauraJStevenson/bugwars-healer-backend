package com.example.bugwarshealerbackend.jpa;

import com.example.bugwarshealerbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

}
