package co.uniquindio.controller;


import co.uniquindio.dtos.request.PasswordUpdateRequest;
import co.uniquindio.dtos.request.RegisterRequest;
import co.uniquindio.dtos.request.UserUpdateRequest;
import co.uniquindio.dtos.response.PaginatedUserResponse;
import co.uniquindio.dtos.response.UserResponse;
import co.uniquindio.service.UserService;
import co.uniquindio.util.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationService authService;


    @GetMapping
    public Optional<PaginatedUserResponse> getUsers(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate registerDate,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userService.getUsers(
                fullName, email, registerDate, age, status, order, page, size
        );
    }

    @PostMapping
    public Optional<UserResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        return Optional.of(authService.register(request));
    }

    @GetMapping("/{id}")
    public Optional<UserResponse> getUser(@PathVariable String id){
        return userService.getUserById(id);
    }

    @PatchMapping("/{id}")
    public Optional<ResponseEntity<Void>> updatePassword(@PathVariable String id, @Valid @RequestBody PasswordUpdateRequest request){
         userService.updatePassword(id, request);
         return Optional.of(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PutMapping("/{id}")
    public Optional<UserResponse> updateUser (@PathVariable String id, @Valid @RequestBody UserUpdateRequest request) {
        return userService.updateUser(id,request);
    }

    @DeleteMapping("/{id}")
    public Optional <ResponseEntity<Void>> deleteUser(@PathVariable String id)
    {
        userService.deleteUser(id);
        return Optional.of(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

}
