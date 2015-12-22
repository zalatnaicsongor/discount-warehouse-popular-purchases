package hu.zalatnai.discountwarehouse.popularpurchases.purchases;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super(String.format("User with username of '%s' was not found", username));
    }
}
