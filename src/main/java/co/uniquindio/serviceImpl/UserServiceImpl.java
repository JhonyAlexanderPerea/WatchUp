package co.uniquindio.serviceImpl;

import co.uniquindio.dtos.common.PaginatedContent;
import co.uniquindio.dtos.request.AccountActivationRequest;
import co.uniquindio.dtos.request.PasswordUpdateRequest;
import co.uniquindio.dtos.request.RegisterRequest;
import co.uniquindio.dtos.request.UserUpdateRequest;
import co.uniquindio.dtos.response.PaginatedUserResponse;
import co.uniquindio.dtos.response.UserResponse;
import co.uniquindio.enums.Role;
import co.uniquindio.enums.UserStatus;
import co.uniquindio.exceptions.ApiExceptions;
import co.uniquindio.mappers.UserMapper;
import co.uniquindio.model.User;
import co.uniquindio.repository.UserRepository;
import co.uniquindio.service.UserService;
import co.uniquindio.util.EmailService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final MongoTemplate mongoTemplate;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    @Autowired
    public UserServiceImpl(MongoTemplate mongoTemplate,
                           UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder, EmailService emailService) {
        this.mongoTemplate = mongoTemplate;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public Optional<PaginatedUserResponse> getUsers(
            String fullName,
            String email,
            LocalDate registerDate,
            Integer age,
            String status,
            String order,
            int page,
            int size
    ) {
        Sort.Direction direction = order != null && order.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Query query = new Query();

        if (fullName != null && !fullName.isEmpty()) {
            query.addCriteria(Criteria.where("fullName").regex(fullName, "i"));
        }
        if (email != null && !email.isEmpty()) {
            query.addCriteria(Criteria.where("email").is(email));
        }
        if (registerDate != null) {
            query.addCriteria(Criteria.where("registerDate").is(registerDate));
        }
        if (age != null) {
            query.addCriteria(Criteria.where("age").is(age));
        }
        if (status != null && !status.isEmpty()) {
            query.addCriteria(Criteria.where("status").is(status));
        }

        long total = mongoTemplate.count(query, User.class);

        query.with(PageRequest.of(page, size, Sort.by(direction, "id")));

        List<User> users = mongoTemplate.find(query, User.class);

        List<UserResponse> userResponses = users.stream()
                .map(userMapper::userToUserResponse)
                .toList();

        PaginatedContent pagination = PaginatedContent.builder()
                .totalPages((int) Math.ceil((double) total / size))
                .totalElements((int) total)
                .currentPage(page)
                .pageSize(size)
                .build();

        return Optional.of(PaginatedUserResponse.builder()
                .users(userResponses)
                .paginated(pagination)
                .build());
    }

    @Override
    public Optional<UserResponse> registerUser(RegisterRequest request) {
        log.debug("Iniciando registro de usuario con email: {}", request.email());

        if (existsByEmail(request.email())) {
            throw new ApiExceptions.EmailAlreadyExistsException("El email ya está registrado");
        }

        try {

            String activationCode = generateActivationCode();
            LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(15);

            // Crear y guardar usuario
            User user = userMapper.registerRequestToUser(request);
            user.setPassword(passwordEncoder.encode(request.password()));
            user.setStatus(UserStatus.INACTIVE);
            user.setActivationCode(activationCode);
            user.setActivationCodeExpiry(expiryTime);
            log.debug("Usuario mapeado antes de guardar: {}", user);

            User savedUser = userRepository.save(user);
            log.debug("Usuario guardado exitosamente con ID: {}", savedUser.getId());

            // Enviar email de activación
            emailService.sendActivationEmail(savedUser.getEmail(), activationCode);


            return Optional.of(userMapper.userToUserResponse(savedUser));
        } catch (Exception e) {
            log.error("Error al registrar usuario: ", e);
            throw new ApiExceptions.InternalServerErrorException("Error al guardar el usuario: " + e.getMessage());
        }
    }


    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<UserResponse> getUserById(String id) {
        log.debug("Buscando usuario con ID: {}", id);

        return Optional.of(userRepository.findById(id)
                .map
                ( user -> {
                            log.debug("Usuario encontrado con ID: {}", id);
                            return userMapper.userToUserResponse(user);
                        }
                )
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado con ID: {}", id);
                    return new ApiExceptions.NotFoundException(
                        "Usuario no encontrado"
                    );
                }));
    }

    @Override
    public Optional<Void>updatePassword(String id, PasswordUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new ApiExceptions.InvalidPasswordException("La contraseña actual no es correcta");
        }

        // Validar que la nueva contraseña no sea igual a la actual
        if (passwordEncoder.matches(request.newPassword(), user.getPassword())) {
            throw new ApiExceptions.InvalidPasswordException("La nueva contraseña no puede ser igual a la actual");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
        log.info("Contraseña actualizada con éxito para el usuario con ID: {}", id);

        return Optional.empty();
    }

    @Override
    public Optional<UserResponse> updateUser(String id, UserUpdateRequest request) {
        log.debug("Iniciando actualización de usuario con ID: {}", id);

        return userRepository.findById(id)
                .map(existingUser -> {
                    try {
                        // Actualizar nombre completo si no es nulo ni vacío
                        Optional.ofNullable(request.fullName())
                                .filter(name -> !name.trim().isEmpty())
                                .ifPresent(name -> {
                                    existingUser.setFullName(name);
                                    log.debug("Nombre actualizado a: {}", name);
                                });

                        // Actualizar email si no es nulo ni vacío, verificando duplicados
                        Optional.ofNullable(request.email())
                                .filter(email -> !email.trim().isEmpty())
                                .ifPresent(email -> {
                                    if (!email.equals(existingUser.getEmail()) && existsByEmail(email)) {
                                        throw new ApiExceptions.EmailAlreadyExistsException("El email ya está registrado");
                                    }
                                    existingUser.setEmail(email);
                                    log.debug("Email actualizado a: {}", email);
                                });

                        // Actualizar ciudad si no es nula ni vacía
                        Optional.ofNullable(request.city())
                                .filter(city -> !city.trim().isEmpty())
                                .ifPresent(city -> {
                                    existingUser.setCity(city);
                                    log.debug("Ciudad actualizada a: {}", city);
                                });

                        // Actualizar número de teléfono si no es nulo ni vacío
                        Optional.ofNullable(request.phoneNumber())
                                .filter(phone -> !phone.trim().isEmpty())
                                .ifPresent(phone -> {
                                    existingUser.setPhoneNumber(phone);
                                    log.debug("Teléfono actualizado a: {}", phone);
                                });

                        // Actualizar dirección si no es nula ni vacía
                        Optional.ofNullable(request.address())
                                .filter(address -> !address.trim().isEmpty())
                                .ifPresent(address -> {
                                    existingUser.setAddress(address);
                                    log.debug("Dirección actualizada a: {}", address);
                                });

                        User updatedUser = userRepository.save(existingUser);
                        log.info("Usuario actualizado exitosamente con ID: {}", updatedUser.getId());
                        return userMapper.userToUserResponse(updatedUser);
                    } catch (Exception e) {
                        log.error("Error al actualizar usuario: ", e);
                        throw new ApiExceptions.InternalServerErrorException("Error al actualizar el usuario: " + e.getMessage());
                    }
                })
                .map(Optional::of)
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado con ID: {}", id);
                    return new ApiExceptions.ResourceNotFoundException("Usuario no encontrado con ID: " + id);
                });
    }

    @Override
    public void deleteUser(String id) {
        try{
            User user = userRepository.findById(id).get();
            user.setStatus(UserStatus.INACTIVE);
            userRepository.save(user);
            log.info("Usuario con ID: {} se ha desactivado exitosamente", id);
        } catch (Exception e) {
            throw new ApiExceptions.NotFoundException("Usuario no encontrado con ID: " + id);
        }

    }

    @Override
    public void activateAccount(AccountActivationRequest request) {
        User user = userRepository.findUserByEmail(request.email())
                .orElseThrow(() -> new ApiExceptions.NotFoundException("Usuario no encontrado"));

        if (user.getStatus().equals(UserStatus.ACTIVE)) {
            throw new ApiExceptions.InvalidOperationException("La cuenta ya está activada");
        }

        if (user.getActivationCodeExpiry().isBefore(LocalDateTime.now())) {
            throw new ApiExceptions.InvalidOperationException("El código de activación ha expirado");
        }

        if (!user.getActivationCode().equals(request.activationCode())) {
            throw new ApiExceptions.InvalidOperationException("Código de activación inválido");
        }

        user.setStatus(UserStatus.ACTIVE);
        user.setActivationCode(null);
        user.setActivationCodeExpiry(null);
        userRepository.save(user);
    }

    private String generateActivationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }


}