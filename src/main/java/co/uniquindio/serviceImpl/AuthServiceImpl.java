package co.uniquindio.serviceImpl;

import co.uniquindio.dtos.request.auth.ActivateRequest;
import co.uniquindio.dtos.request.auth.LoginRequest;
import co.uniquindio.dtos.request.auth.RegisterRequest;
import co.uniquindio.dtos.response.UserResponse;
import co.uniquindio.dtos.response.auth.BasicUserResponse;
import co.uniquindio.mappers.UserMapper;
import co.uniquindio.model.User;
import co.uniquindio.service.AuthService;
import co.uniquindio.service.EmailService;
import co.uniquindio.util.ActivationCodeGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import co.uniquindio.repository.UserRepository;
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ActivationCodeGenerator activationCodeGenerator;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserMapper userMapper;

    public AuthServiceImpl(UserRepository userRepository,
                           ActivationCodeGenerator activationCodeGenerator,
                           PasswordEncoder passwordEncoder,
                           EmailService emailService,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.activationCodeGenerator = activationCodeGenerator;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.userMapper = userMapper;
    }

    @Override
    public BasicUserResponse registerUser(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent())
            throw new RuntimeException("Email ya registrado");

        User user = userMapper.registerRequestToUser(registerRequest);
        User savedUser = userRepository.save(user);
        emailService.sendActivationEmail(savedUser.getEmail(), "Activación de cuenta", savedUser.getActivationCode());
        return new BasicUserResponse(savedUser.getId(), savedUser.getEmail(), savedUser.getFullName());
    }

    @Override
    public void activateUser(ActivateRequest request) {

    }

    @Override
    public UserResponse login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public void forgotPassword(String email) {

    }

    @Override
    public void resetPassword(String email, String resetCode, String newPassword) {

    }
}
