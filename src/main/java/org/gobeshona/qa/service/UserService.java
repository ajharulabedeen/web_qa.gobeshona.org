package org.gobeshona.qa.service;

import org.gobeshona.qa.dto.UserDto;
import org.gobeshona.qa.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
