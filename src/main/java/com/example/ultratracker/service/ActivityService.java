package com.example.ultratracker.service;

import com.example.ultratracker.model.Activity;
import com.example.ultratracker.model.User; // Importiere User
import com.example.ultratracker.repository.ActivityRepository;
import com.example.ultratracker.repository.UserRepository; // Importiere UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository; // Füge UserRepository hinzu

    @Autowired
    public ActivityService(ActivityRepository activityRepository, UserRepository userRepository) { // Konstruktor anpassen
        this.activityRepository = activityRepository;
        this.userRepository = userRepository; // Initialisiere UserRepository
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    // NEU: Nur Aktivitäten eines bestimmten Benutzers abrufen
    public List<Activity> getActivitiesByUserId(Long userId) {
        return activityRepository.findByUserId(userId);
    }

    public Optional<Activity> getActivityById(Long id) {
        return activityRepository.findById(id);
    }

    // NEU: createUser nimmt jetzt auch eine userId entgegen
    public Activity createActivity(Activity activity, Long userId) {
        // Finde den User anhand der ID
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            activity.setUser(userOptional.get()); // Setze den gefundenen User in die Activity
            return activityRepository.save(activity);
        }
        // Wenn der User nicht gefunden wurde, gib null zurück oder wirf eine spezifische Exception
        return null; // Wird später durch eine Exception ersetzt
    }

    // updateUser und deleteUser müssen ebenfalls überarbeitet werden,
    // um sicherzustellen, dass nur der Besitzer die Aktivität ändern/löschen kann.
    // Das machen wir im nächsten Schritt, wenn wir Validierung und Sicherheit behandeln.
    public Activity updateActivity(Long id, Activity activityDetails) {
        return activityRepository.findById(id)
                .map(activity -> {
                    activity.setName(activityDetails.getName());
                    activity.setDescription(activityDetails.getDescription());
                    activity.setDurationMinutes(activityDetails.getDurationMinutes());
                    // Den User hier nicht ändern, da er fest mit der Aktivität verbunden ist
                    return activityRepository.save(activity);
                })
                .orElse(null);
    }

    public boolean deleteActivity(Long id) {
        if (activityRepository.existsById(id)) {
            activityRepository.deleteById(id);
            return true;
        }
        return false;
    }
}