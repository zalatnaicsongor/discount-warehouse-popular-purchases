package hu.zalatnai.discountwarehouse.users.infrastructure;

import hu.zalatnai.discountwarehouse.users.UserExistenceChecker;
import hu.zalatnai.discountwarehouse.users.UsersConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UsersInfrastructureConfiguration {
    @Autowired
    private UsersConfiguration usersConfiguration;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public UserExistenceChecker userExistenceChecker() {
        return new CachingUserExistenceCheckerDecorator(new RESTUserExistenceChecker(restTemplate, usersConfiguration));
    }
}
