package com.faceit.faceit.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
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


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_players",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private Set<Player> favoritePlayers = new HashSet<>();


    @PreRemove
    public void removeUser() {
       country.getUsers().removeAll(Collections.singleton(this));
    }


}
