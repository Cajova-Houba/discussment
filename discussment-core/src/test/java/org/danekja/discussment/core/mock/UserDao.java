package org.danekja.discussment.core.mock;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.dao.GenericDao;

import java.util.Collection;
import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 *
 * The interface extends GenericDao on methods for working with users in a database
 */
public interface UserDao extends GenericDao<Long, User> {

    /**
     * Get an user in a database based on his username.
     *
     * @param username username of a user
     * @return User
     */
    User getUserByUsername(String username);

    /**
     * Get all users in a database.
     *
     * @return list of User
     */
    List<User> getUsers();

    /**
     * Returns a list of users with given ids.
     *
     * @param userIds Ids of users to be returned.
     * @return Found users.
     */
    List<IDiscussionUser> getUsersByIds(Collection<String> userIds);
}
