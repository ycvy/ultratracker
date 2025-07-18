package com.example.ultratracker.repository;

import com.example.ultratracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA stellt hier automatisch CRUD-Methoden bereit (save, findById, findAll, delete, etc.)
    // Du kannst hier bei Bedarf auch eigene Query-Methoden hinzufügen, z.B.:
    // Optional<User> findByUsername(String username);
    // Optional<User> findByEmail(String email);
}