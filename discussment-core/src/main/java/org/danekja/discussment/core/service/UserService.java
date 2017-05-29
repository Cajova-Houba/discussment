package org.danekja.discussment.core.service;

import org.danekja.discussment.core.domain.Permission;
import org.danekja.discussment.core.domain.User;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 */
public interface UserService {
    User addUser(User entity, Permission permission);

    List<User> getUsers();

    User getUserById(long userId);

    User getUserByUsername(String username);
}
