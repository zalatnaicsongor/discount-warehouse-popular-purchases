package hu.zalatnai.discountwarehouse.users.infrastructure;

import hu.zalatnai.discountwarehouse.users.UserExistenceChecker;
import hu.zalatnai.discountwarehouse.users.UserNotFoundException;
import hu.zalatnai.discountwarehouse.users.UsersConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
class RESTUserExistenceChecker implements UserExistenceChecker {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final RestTemplate restTemplate;

    private final UsersConfiguration usersConfiguration;

    @Autowired
    RESTUserExistenceChecker(
        RestTemplate restTemplate, UsersConfiguration usersConfiguration
    ) {
        this.restTemplate = restTemplate;
        this.usersConfiguration = usersConfiguration;
    }

    @Override
    public void checkIfUserExists(String username) {
        //it would've been better if the API returned 404 instead of 200 if no user were found by the supplied username
        log.info("Started retrieveing user " + username);
        String response = restTemplate.getForObject(usersConfiguration.getBaseUrl() + "/" + username, String.class);
        log.info("Retrieved user " + username);
        if (response == null || response.equals("{}")) {
            throw new UserNotFoundException(username);
        }


    }
}
