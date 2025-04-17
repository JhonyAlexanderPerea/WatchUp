package co.uniquindio.service;

import co.uniquindio.dtos.request.RegisterRequest;
import co.uniquindio.dtos.response.PaginatedUserResponse;
import co.uniquindio.dtos.response.UserResponse;

import java.time.LocalDate;

public interface UserService {
    PaginatedUserResponse getUsers(
            String fullName,
            String email,
            LocalDate registerDate,
            Integer age,
            String status,
            String order,
            int page,
            int size
    );
    UserResponse registerUser(RegisterRequest request);

    boolean existsByEmail(String email);


}
