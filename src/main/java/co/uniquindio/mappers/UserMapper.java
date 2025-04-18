package co.uniquindio.mappers;

import co.uniquindio.dtos.request.RegisterRequest;
import co.uniquindio.dtos.response.UserResponse;
import co.uniquindio.model.User;
import co.uniquindio.util.LocationService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Autowired
    LocationService locationService = new LocationService();

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activationCode", ignore = true)
    @Mapping(target = "activationCodeExpiry", ignore = true)
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "status", constant = "INACTIVE")
    @Mapping(target = "location", expression = "java(locationService.getCurrentLocation())")
    User registerRequestToUser(RegisterRequest request);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "role", source = "role")
    UserResponse userToUserResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "status", constant = "INACTIVE")
    UserResponse registerRequestToUserResponse(RegisterRequest request);

    List<UserResponse> usersToUserResponses(List<User> users);
}