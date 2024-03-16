package com.faceit.faceit.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collections;

@Entity
@Table(name="users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;
    private String role;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    private Country country;
    @PreRemove
    public void removeUser() {
       country.getUsers().removeAll(Collections.singleton(this));
    }


}
