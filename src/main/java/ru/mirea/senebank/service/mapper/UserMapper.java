package ru.mirea.senebank.service.mapper;

import org.mapstruct.Mapper;
import ru.mirea.senebank.dto.registration.UserRegisterRequest;
import ru.mirea.senebank.entity.User;
import ru.mirea.senebank.security.UserModel;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserRegisterRequest request);
}
