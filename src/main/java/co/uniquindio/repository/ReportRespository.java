package co.uniquindio.repository;

import co.uniquindio.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRespository extends MongoRepository <Report, String> {

}
