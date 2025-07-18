-- Fügt die user_id Spalte zur activity Tabelle hinzu
ALTER TABLE activity
ADD COLUMN user_id BIGINT;

-- Aktualisiere bestehende Aktivitäten mit einer Standard-User-ID, falls nötig
-- Beachte: Wenn du noch keine Benutzer in der DB hast, musst du diesen Teil auskommentieren
-- oder zuerst einen Benutzer manuell einfügen, bevor du diese Migration ausführst,
-- da der FOREIGN KEY Constraint sonst fehlschlägt.
UPDATE activity SET user_id = 1 WHERE user_id IS NULL;

-- Fügt den FOREIGN KEY Constraint hinzu
ALTER TABLE activity
ADD CONSTRAINT fk_activity_user
FOREIGN KEY (user_id) REFERENCES users (id);

-- Macht die user_id Spalte NOT NULL, nachdem der FOREIGN KEY Constraint hinzugefügt wurde
-- und sichergestellt ist, dass alle bestehenden Aktivitäten eine user_id haben.
ALTER TABLE activity
ALTER COLUMN user_id SET NOT NULL;