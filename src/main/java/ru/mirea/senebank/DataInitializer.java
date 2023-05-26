package ru.mirea.senebank;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.mirea.senebank.entity.Account;
import ru.mirea.senebank.entity.Role;
import ru.mirea.senebank.entity.User;
import ru.mirea.senebank.model.UserRole;
import ru.mirea.senebank.repository.AccountRepository;
import ru.mirea.senebank.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String EMAIL = "admin@mail.com";
    private static final String PASSWORD = "pass123";

    @PostConstruct
    private void generate() {
        createAdmin();
    }

    private void createAdmin() {

        Role roleAdmin = new Role();
        roleAdmin.setName(UserRole.ADMIN);

        Role roleUser = new Role();
        roleUser.setName(UserRole.USER);

        if (userRepository.findByEmail(EMAIL).isPresent()) {
            return;
        }

        User user = User.builder()
                .email(EMAIL)
                .password(passwordEncoder.encode(PASSWORD))
                .roles(List.of(roleAdmin, roleUser))
                .registrationDate(LocalDate.now())
                .build();

        user.setAccounts(List.of(new Account()));
        userRepository.save(user);
    }

}
