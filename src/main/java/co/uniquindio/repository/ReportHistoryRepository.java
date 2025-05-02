package co.uniquindio.repository;

import co.uniquindio.dtos.response.ReportHistoryResponse;
import co.uniquindio.model.ReportHistory;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportHistoryRepository extends MongoRepository<ReportHistory, String> {

    @Aggregation({
            "{ $lookup: { from: 'report', localField: 'reportId', foreignField: '_id', as: 'report' } }",
            "{ $unwind: '$report' }",
            "{ $project: { id:1 , reportId:1, action:1, description:1, reportDateCreation: '$report.creationDate', modifiedBy: 1, reportTitle: '$report.title', modifiedDate: 1}}"
    })
    List<ReportHistoryResponse> findReportHistoriesdWithTitleAndCreationDate();

    @Aggregation({
            "{ $match: { reportId: ?0 } }",
            "{ $lookup: { from: 'report', localField: 'reportId', foreignField: '_id', as: 'report' } }",
            "{ $unwind: '$report' }",
            "{ $project: { reportId:1, action:1, description:1, reportDateCreation: '$report.creationDate', modifiedBy: 1, reportTitle: '$report.title', modifiedDate: 1}}"
    })
    List<ReportHistoryResponse> findReportHistoriesByReportIdWithTitleAndCreationDate(String reportId);

    @Aggregation({
            "{ $match: { 'registerDate' : {$get : ?0 , $lte : ?1 } } }",
            "{ $lookup: { from: 'report', localField: 'reportId', foreignField: '_id', as: 'report' } }",
            "{ $unwind: '$report' }",
            "{ $project: { reportId:1, action:1, description:1, reportDateCreation: '$report.creationDate', modifiedBy: 1, reportTitle: '$report.title', modifiedDate: 1}}"
    })
    List<ReportHistoryResponse> findReportHistoriesByRegisterDateBetweenWithTitleAndCreationDate(LocalDateTime date1, LocalDateTime date2 );

}
