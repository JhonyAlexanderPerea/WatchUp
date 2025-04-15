package co.uniquindio.service;

import co.uniquindio.dtos.request.PasswordUpdateRequest;
import co.uniquindio.dtos.request.UserUpdateRequest;
import co.uniquindio.dtos.request.auth.RegisterRequest;
import co.uniquindio.dtos.response.PaginatedUserResponse;
import co.uniquindio.dtos.response.UserResponse;
import co.uniquindio.dtos.response.auth.BasicUserResponse;
import co.uniquindio.model.User;

public interface UserService {
    PaginatedUserResponse getUsers(int page, int size);
    UserResponse getUser(String id);
    Boolean updatePassword(PasswordUpdateRequest passwordUpdateRequest);
    UserResponse updateUser(UserUpdateRequest userUpdateRequest);
    void deactivateUser(User user);
}
