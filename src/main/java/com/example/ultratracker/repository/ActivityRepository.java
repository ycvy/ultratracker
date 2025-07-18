package com.example.ultratracker.repository;

import com.example.ultratracker.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    // NEU: Suchmethode, um alle Aktivitäten eines bestimmten Benutzers zu finden
    List<Activity> findByUserId(Long userId);
}