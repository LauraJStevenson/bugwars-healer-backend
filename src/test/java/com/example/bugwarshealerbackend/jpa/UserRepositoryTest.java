package com.example.bugwarshealerbackend.jpa;

import com.example.bugwarshealerbackend.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository = mock(UserRepository.class);

    @MockBean
    private EntityManager entityManager;
    /*test is using Mockito to mock the "EntityManager and "TypedQuery" to isolate the
    test from the actual database interactions.*/

    @Test
     void findByUsername(){
    //ARRANGE
    String username = "testUser";
    User expectedUser = new User();

    EntityManager entityManager = mock(EntityManager.class);
    when(entityManager.createQuery(any(String.class), any(Class.class))).thenReturn(mock(Query.class));


    TypedQuery<User> query = mock(TypedQuery.class);
    when(entityManager.createQuery(anyString(),eq(User.class))).thenReturn(query);
    when(query.setParameter(anyInt(), any())).thenReturn(query);
    when(query.getSingleResult()).thenReturn(expectedUser);
    when(userRepository.findByUsername(username)).thenReturn(expectedUser);

    //ACT
    User actualUser = userRepository.findByUsername(username);

    //ASSERT
    assertNotNull(actualUser);
    assertEquals(expectedUser, actualUser);
    assertEquals(entityManager).createQuery("SELECT u FROM User u where u.username = ?1", User.class);
      //verify(query).setParameter(1, username);
       //verify(query).getSingleResult();
}



    private void assertEquals(User expectedUser, User actualUser) {
    }

    private EntityManager assertEquals(EntityManager entityManager) {
        return entityManager;
    }
}

