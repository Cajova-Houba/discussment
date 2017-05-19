package org.danekja.discussment.core.dao;

import org.danekja.discussment.core.domain.User;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 */
public interface UserDao extends GenericDao<User> {
    User getUserByUsername(String username);

    List<User> getUsers();
}
