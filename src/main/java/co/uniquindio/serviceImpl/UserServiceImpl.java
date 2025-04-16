package co.uniquindio.serviceImpl;

import co.uniquindio.dtos.request.PasswordUpdateRequest;
import co.uniquindio.dtos.request.UserUpdateRequest;
import co.uniquindio.dtos.response.PageInfoResponse;
import co.uniquindio.dtos.response.PaginatedUserResponse;
import co.uniquindio.dtos.response.UserResponse;
import co.uniquindio.mappers.UserMapper;
import co.uniquindio.model.User;
import co.uniquindio.repository.UserRepository;
import co.uniquindio.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PageInfoResponse pageInfo;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.pageInfo = new PageInfoResponse();
    }

    @Override
    public PaginatedUserResponse getUsers(int page, int size) {
        List<UserResponse> users = new ArrayList<UserResponse>();
        for (User user : userRepository.findAll()) {
            users.add(userMapper.userToUserResponse(user));
        }
        return new PaginatedUserResponse(users,pageInfo);
    }

    @Override
    public UserResponse getUser(String userId) {
        return userRepository.findById(userId).map(userMapper::userToUserResponse).orElse(null);
    }

    @Override
    public void updatePassword(String userId, PasswordUpdateRequest passwordUpdateRequest) {
        //TODO
    }

    @Override
    public UserResponse updateUser(String userId, UserUpdateRequest userUpdateRequest) {
        //TODO
        return null;
    }

    @Override
    public void deactivateUser(String userId) {
        //TODO
    }
}
