package com.berchtesgaden.explorer.domain;
import jakarta.persistence.*;
import lombok.*;

@Entity // saying JPA this is a class = table in database
@Table(name = "users") // named table in database in users, cause user already exits in Postgress
@Getter
@Setter
@NoArgsConstructor // constructor without argumetns
@AllArgsConstructor // constructor with all fields
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
