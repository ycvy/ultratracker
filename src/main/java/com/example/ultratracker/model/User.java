package com.example.ultratracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "users") // Wichtig: "user" ist ein reserviertes Schlüsselwort in vielen Datenbanken, daher "users"
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    // Später werden wir hier ein Passwort-Feld hinzufügen, wenn wir uns mit Sicherheit befassen
    // private String password;
}