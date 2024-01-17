package com.example.bugwarshealerbackend.jpa;

import com.example.bugwarshealerbackend.model.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    
    //retrieved the user from username
    @Query(value = "SELECT u FROM User u where u.username = ?1")
    @Cacheable("users")
    User findByUsername(String username);

    @Override
    @CachePut(cacheNames = "users", key = "#entity.username")
    <S extends User> S save(S entity);

    @Override
    @CacheEvict(cacheNames = "users", key = "#entity.username")
    void delete(User entity);
}
