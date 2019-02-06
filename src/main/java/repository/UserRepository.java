package repository;

import model.User;

import java.util.Collection;

/**
 * User repository
 */
public class UserRepository implements Repository {

    /**
     * Get all users
     *
     * @return {@link Collection} of {@link User}
     */
    public Collection<User> getAllUsers() {
        return retrieveAll(User.class);
    }

    /**
     * Get user with given user id
     *
     * @param id User id
     * @return {@link User} instance
     */
    public User getUser(int id) {
        return retrieve(User.class, id);
    }

    /**
     * Save the given user
     *
     * @param user {@link User} instance
     */
    public void saveUser(User user) {
        persist(user);
    }
}
