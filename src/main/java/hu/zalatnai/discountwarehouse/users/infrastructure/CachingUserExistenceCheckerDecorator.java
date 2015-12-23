package hu.zalatnai.discountwarehouse.users.infrastructure;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import hu.zalatnai.discountwarehouse.users.UserExistenceChecker;
import hu.zalatnai.discountwarehouse.users.UserNotFoundException;

class CachingUserExistenceCheckerDecorator implements UserExistenceChecker {
    private final UserExistenceChecker userExistenceChecker;

    private final LoadingCache<String, Boolean> cache;

    //todo: inject cache, maybe add configurable parameters.
    CachingUserExistenceCheckerDecorator(UserExistenceChecker userExistenceChecker) {
        this.userExistenceChecker = userExistenceChecker;
        this.cache = Caffeine.newBuilder().maximumSize(10000).expireAfterWrite(
            10,
            TimeUnit.MINUTES
        ).build(username -> {
            try {
                userExistenceChecker.checkIfUserExists(username);
                return true;
            } catch (UserNotFoundException e) {
                return false;
            }
        });
    }


    @Override
    public void checkIfUserExists(String username) {
        if (!cache.get(username)) {
            throw new UserNotFoundException(username);
        }
    }
}
