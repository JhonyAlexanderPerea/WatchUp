package co.uniquindio.repository;

import co.uniquindio.model.StatusHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatusHistoryRespository extends MongoRepository <StatusHistory, String> {

}
