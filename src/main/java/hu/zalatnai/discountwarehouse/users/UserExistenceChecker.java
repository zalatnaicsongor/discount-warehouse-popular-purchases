package hu.zalatnai.discountwarehouse.users;

/**
 * Created by zalatnaicsongor on 22/12/2015.
 */
public interface UserExistenceChecker {
    /**
     * Checks if a user with the supplied username exists. Returns if a user indeed exists, throws otherwise.
     * @throws UserNotFoundException if no users with the supplied username could be found
     * @param username the username to be checked
     */
    void checkIfUserExists(String username);
}
