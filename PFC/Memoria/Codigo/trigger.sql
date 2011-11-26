CREATE TRIGGER DeleteEntity
AFTER DELETE ON notificationsUsers
FOR EACH ROW
BEGIN
DECLARE
  cont INT;
  SELECT COUNT(*) INTO cont FROM notificationsUsers WHERE idNotification = OLD.idNotification;
  IF (cont = 0) THEN
    DELETE FROM notifications WHERE id = OLD.idNotification;
  END IF;
END$$