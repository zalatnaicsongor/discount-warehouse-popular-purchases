package hu.zalatnai.discountwarehouse.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
class RESTUserExistenceChecker implements UserExistenceChecker {

    private final RestTemplate restTemplate;

    private final UsersConfiguration usersConfiguration;

    @Autowired
    RESTUserExistenceChecker(RestTemplate restTemplate, UsersConfiguration usersConfiguration) {
        this.restTemplate = restTemplate;
        this.usersConfiguration = usersConfiguration;
    }

    @Override
    public void checkIfUserExists(String username) {
        User response = restTemplate.getForObject(usersConfiguration.getBaseUrl() + "/" + username, User.class);

        if (response.getUsername() == null || response.getUsername().equals("")) {
            throw new UserNotFoundException(username);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class User {
        @JsonProperty
        private String email;

        @JsonProperty
        private String username;

        public User(String email, String username) {
            this.email = email;
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public String getUsername() {
            return username;
        }
    }
}
