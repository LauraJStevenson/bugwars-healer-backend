package com.example.bugwarshealerbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registered_users")
public class User {

    private long id;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private int counter;
    private boolean activated;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    @Column(name= "username", nullable = false)
    public String getUsername(){
        return username;
    }

    @Column(name = "firstname", nullable = false)
    public String getFirstname() {
        return firstname;
    }

    @Column(name = "lastname", nullable = false)
    public String getLastname(){
        return lastname;
    }

    @Column(name = "password_hash", nullable = false)
    public String getPassword(){
        return password;
    }

    @Column(name = "email", nullable = false)
    public String getEmail(){
        return email;
    }

    @Column(name = "counter", nullable = true)
    public int getCounter(){
        return counter;
    }

    @Column(name = "activated", nullable = true)
    public boolean getActivated(){
        return activated;
    }


    public User(int id, String username, String firstname, String lastname, String password, String email, int counter) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.counter = counter;
    }
}
