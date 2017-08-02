package org.danekja.discussment.example.core;

import org.danekja.discussment.core.domain.BaseEntity;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.IDiscussionUser;
import org.danekja.discussment.core.domain.Permission;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.ArrayList;
import java.util.List;

import static org.danekja.discussment.example.core.User.GET_BY_USERNAME;
import static org.danekja.discussment.example.core.User.GET_USERS;

/**
 * Created by Martin Bláha on 04.01.17.
 *
 * The class represents a user in the discussion.
 */

@Entity
@NamedQueries({
    @NamedQuery(name = GET_BY_USERNAME, query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = GET_USERS, query = "SELECT u FROM User u")
})
// todo: replace with interface
public class User extends BaseEntity implements IDiscussionUser {

    /**
     * The constant contains name of query for getting an user by username
     */
    public static final String GET_BY_USERNAME = "User.getByUsername";

    /**
     * The constant contains name of query for getting all users in a database
     */
    public static final String GET_USERS = "User.getUsers";

    /**
     * Username of the user. User name must be unique.
     */
    @Column(unique=true)
    private String username;

    /**
     * Name of the user.
     */
    private String name;

    /**
     * Lastname of the user.
     */
    private String lastname;


    public User() {}

    public User(String username, String name, String lastname) {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}