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

}
