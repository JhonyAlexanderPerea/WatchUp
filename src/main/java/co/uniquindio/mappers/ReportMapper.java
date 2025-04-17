package co.uniquindio.mappers;
import co.uniquindio.dtos.request.ReportRequest;
import co.uniquindio.model.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "status", constant = "REGISTERED")
    @Mapping(target = "creationDate", expression ="java(java.time.LocalDateTime.now())")
    Report parseOf(ReportRequest reportRequest);

}
