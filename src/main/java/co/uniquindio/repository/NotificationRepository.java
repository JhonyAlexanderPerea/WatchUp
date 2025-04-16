package co.uniquindio.repository;

import co.uniquindio.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository <Notification, String> {

}
