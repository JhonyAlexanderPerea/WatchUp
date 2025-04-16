package co.uniquindio.mappers;

import co.uniquindio.dtos.request.auth.RegisterRequest;
import co.uniquindio.dtos.response.UserResponse;
import co.uniquindio.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserResponse userToUserResponse(User user);
    User registerRequestToUser(RegisterRequest registerRequest);
}
