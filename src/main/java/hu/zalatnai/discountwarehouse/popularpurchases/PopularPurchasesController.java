package hu.zalatnai.discountwarehouse.popularpurchases;

import java.util.List;

import hu.zalatnai.discountwarehouse.products.ProductWithRecentPurchases;
import hu.zalatnai.discountwarehouse.users.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PopularPurchasesController {
    private final PopularPurchasesService popularPurchasesService;

    @Autowired
    public PopularPurchasesController(PopularPurchasesService popularPurchasesService) {
        this.popularPurchasesService = popularPurchasesService;
    }

    @RequestMapping(value = "/api/recent_purchases/{username}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ProductWithRecentPurchases> getPopularPurchases(@PathVariable("username") String username) {
        return popularPurchasesService.retrieveMostPopularRecentlyPurchasedProductsOfUser(username);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFound(UserNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}