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
    UserResponse getUser(String userId);
    void updatePassword(String userId, PasswordUpdateRequest passwordUpdateRequest);
    UserResponse updateUser(String userId, UserUpdateRequest userUpdateRequest);
    void deactivateUser(String userId);
}
