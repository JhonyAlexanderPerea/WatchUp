package co.uniquindio.serviceImpl;

import co.uniquindio.dtos.common.PaginatedContent;
import co.uniquindio.dtos.request.RegisterRequest;
import co.uniquindio.dtos.response.PaginatedUserResponse;
import co.uniquindio.dtos.response.UserResponse;
import co.uniquindio.exceptions.ApiExceptions;
import co.uniquindio.mappers.UserMapper;
import co.uniquindio.model.User;
import co.uniquindio.repository.UserRepository;
import co.uniquindio.service.UserService;
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
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final MongoTemplate mongoTemplate;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(MongoTemplate mongoTemplate,
                           UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder) {
        this.mongoTemplate = mongoTemplate;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public PaginatedUserResponse getUsers(
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

        return PaginatedUserResponse.builder()
                .users(userResponses)
                .paginated(pagination)
                .build();
    }

    @Override
    public UserResponse registerUser(RegisterRequest request) {
        log.debug("Iniciando registro de usuario con email: {}", request.getEmail());

        if (existsByEmail(request.getEmail())) {
            throw new ApiExceptions.EmailAlreadyExistsException("El email ya está registrado");
        }

        try {
            User user = userMapper.registerRequestToUser(request);
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            log.debug("Usuario mapeado antes de guardar: {}", user);

            User savedUser = userRepository.save(user);
            log.debug("Usuario guardado exitosamente con ID: {}", savedUser.getId());

            return userMapper.userToUserResponse(savedUser);
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
    public UserResponse getUserById(String id) {
        log.debug("Buscando usuario con ID: {}", id);

        return userRepository.findById(id)
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
                });
    }
}