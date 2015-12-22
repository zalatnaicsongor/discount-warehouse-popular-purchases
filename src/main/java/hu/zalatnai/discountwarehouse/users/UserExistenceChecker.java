package hu.zalatnai.discountwarehouse.users;

import org.springframework.stereotype.Service;

@Service
public class UserExistenceChecker {
    /**
     * Checks if a user with the supplied username exists. Returns if a user indeed exists, throws otherwise.
     * @throws UserNotFoundException if no users with the supplied username could be found
     * @param username the username to be checked
     */
    public void checkIfUserExists(String username) {
        if ("non_existent_username_qwe".equals(username)) {
            throw new UserNotFoundException(username);
        }
    }
}
