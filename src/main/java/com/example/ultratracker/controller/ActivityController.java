package com.example.ultratracker.controller;

import com.example.ultratracker.model.Activity;
import com.example.ultratracker.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    // Ändere diese Methode, falls du nur Aktivitäten eines bestimmten Benutzers sehen willst,
    // oder belasse sie, um alle Aktivitäten zu sehen (inkl. der user_id).
    // Für den Anfang behalten wir sie so, aber später filtern wir vielleicht.
    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        List<Activity> activities = activityService.getAllActivities();
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable Long id) {
        Optional<Activity> activity = activityService.getActivityById(id);
        return activity.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    // NEU: Endpunkt zum Abrufen von Aktivitäten eines bestimmten Benutzers
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Activity>> getActivitiesByUserId(@PathVariable Long userId) {
        List<Activity> activities = activityService.getActivitiesByUserId(userId);
        return ResponseEntity.ok(activities);
    }


    // NEU: createUser benötigt jetzt die user_id im Pfad
    @PostMapping("/user/{userId}")
    public ResponseEntity<Activity> createActivity(@PathVariable Long userId, @RequestBody Activity activity) {
        Activity createdActivity = activityService.createActivity(activity, userId);
        if (createdActivity != null) {
            return new ResponseEntity<>(createdActivity, HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 Bad Request, wenn User nicht existiert
    }


    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestBody Activity activityDetails) {
        // BEACHTE: Hier ist noch keine Besitzprüfung implementiert!
        // Jeder könnte Aktivitäten aktualisieren. Das machen wir, wenn wir Sicherheit hinzufügen.
        Activity updatedActivity = activityService.updateActivity(id, activityDetails);
        if (updatedActivity != null) {
            return ResponseEntity.ok(updatedActivity);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        // BEACHTE: Auch hier keine Besitzprüfung!
        if (activityService.deleteActivity(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}