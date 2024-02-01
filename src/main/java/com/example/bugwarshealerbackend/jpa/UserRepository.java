package com.example.bugwarshealerbackend.jpa;

import com.example.bugwarshealerbackend.model.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    /**
     * Updates the refresh token for a specific user identified by their userId.
     * This method is used when a new refresh token is generated for a user and needs to be persisted in the database.
     * It's important to keep the refresh token updated to maintain the integrity of the user's authentication state.
     *
     * @param userId The ID of the user for whom the refresh token is being updated.
     * @param refreshToken The new refresh token that will replace the old one.
     */
    @Modifying
    @Query("update User u set u.refreshToken = :refreshToken where u.id = :userId")
    void updateRefreshToken(@Param("userId") Long userId, @Param("refreshToken") String refreshToken);

}
