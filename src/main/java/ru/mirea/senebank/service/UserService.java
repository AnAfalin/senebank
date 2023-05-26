package ru.mirea.senebank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mirea.senebank.dto.info.OperationResultDto;
import ru.mirea.senebank.dto.login.UserLoginRequest;
import ru.mirea.senebank.dto.login.UserLoginResponse;
import ru.mirea.senebank.dto.registration.UserRegisterRequest;
import ru.mirea.senebank.dto.registration.UserRegisterResponse;
import ru.mirea.senebank.dto.security.TokenDto;
import ru.mirea.senebank.entity.Role;
import ru.mirea.senebank.entity.User;
import ru.mirea.senebank.exception.NoSuchUserException;
import ru.mirea.senebank.exception.UserEmailExistException;
import ru.mirea.senebank.model.UserRole;
import ru.mirea.senebank.repository.UserRepository;
import ru.mirea.senebank.security.JwtService;
import ru.mirea.senebank.security.UserModel;
import ru.mirea.senebank.service.mapper.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    public UserRegisterResponse registerUser(UserRegisterRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        User newUser = userMapper.toUser(request);

        if (isExistEmail(request.getEmail())) {
            throw new UserEmailExistException("User with email = '%s' already exist");
        }

        Role role = new Role();
        role.setName(UserRole.USER);
        newUser.setRoles(List.of(role));

        userRepository.save(newUser);

        return UserRegisterResponse.builder()
                .message("User with login='%s' successfully created".formatted(newUser.getEmail()))
                .status(HttpStatus.CREATED.toString())
                .build();
    }

    public OperationResultDto deleteUserById(Integer id) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException("User with id='%s' not found".formatted(id)));

        userRepository.delete(foundUser);

        return OperationResultDto.builder()
                .status(HttpStatus.OK.toString())
                .success(true)
                .message("User with id='%s' successfully deleted".formatted(id))
                .build();
    }

    public UserLoginResponse login(UserLoginRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            throw new NoSuchUserException("User with email = '%s' not found".formatted(request.getEmail()));
        }

        User loadUser = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), loadUser.getPassword())) {
            throw new UsernameNotFoundException("Password not match");
        }

        UserModel user = new UserModel(loadUser);

        TokenDto accessToken = jwtService.generateToken(user.getUsername(), user.getAuthorities());

        return UserLoginResponse.builder()
                .email(user.getUsername())
                .roles(user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .accessToken(accessToken)
                .build();
    }

    public User findUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException("User with id='%s' not found".formatted(id)));
    }

    private boolean isExistEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
