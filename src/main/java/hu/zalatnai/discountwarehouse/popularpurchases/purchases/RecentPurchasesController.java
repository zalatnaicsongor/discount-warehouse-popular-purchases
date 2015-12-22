package hu.zalatnai.discountwarehouse.popularpurchases.purchases;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RecentPurchasesController {
    @RequestMapping(value = "/api/recent_purchases/{username}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ProductWithRecentPurchasesOutput getRecentPurchases(@PathVariable("username") String username) {
        if ("non_existent_username_qwe".equals(username)) {
            throw new UserNotFoundException(username);
        }

        return null;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFound(UserNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}