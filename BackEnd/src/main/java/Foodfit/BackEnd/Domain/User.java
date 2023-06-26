package Foodfit.BackEnd.Domain;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private int age;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}

