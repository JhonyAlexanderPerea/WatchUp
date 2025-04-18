package co.uniquindio.service;

import co.uniquindio.dtos.request.PasswordUpdateRequest;
import co.uniquindio.dtos.request.RegisterRequest;
import co.uniquindio.dtos.request.UserUpdateRequest;
import co.uniquindio.dtos.response.PaginatedUserResponse;
import co.uniquindio.dtos.response.UserResponse;
import co.uniquindio.enums.Role;
import co.uniquindio.enums.UserStatus;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.Optional;

public interface UserService {
    Optional<PaginatedUserResponse> getUsers(
            String fullName,
            String email,
            LocalDate registerDate,
            Integer age,
            String status,
            String order,
            int page,
            int size
    );
    Optional<UserResponse> registerUser(RegisterRequest request);

    boolean existsByEmail(String email);

    Optional<UserResponse> getUserById(String id);

    Optional<Void> updatePassword(String id, PasswordUpdateRequest request);

    Optional <UserResponse> updateUser(String id, UserUpdateRequest request);

}
