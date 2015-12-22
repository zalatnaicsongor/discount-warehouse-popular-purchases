package hu.zalatnai.discountwarehouse.popularpurchases;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import hu.zalatnai.discountwarehouse.products.ProductWithRecentPurchases;
import hu.zalatnai.discountwarehouse.products.ProductWithRecentPurchasesRepository;
import hu.zalatnai.discountwarehouse.products.Purchase;
import hu.zalatnai.discountwarehouse.products.PurchaseRepository;
import hu.zalatnai.discountwarehouse.users.UserExistenceChecker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class PopularPurchasesService {
    private final UserExistenceChecker userExistenceChecker;

    private final ProductWithRecentPurchasesRepository productWithRecentPurchasesRepository;

    private final PurchaseRepository purchaseRepository;

    @Autowired PopularPurchasesService(
        UserExistenceChecker userExistenceChecker,
        ProductWithRecentPurchasesRepository productWithRecentPurchasesRepository,
        PurchaseRepository purchaseRepository
    ) {
        this.userExistenceChecker = userExistenceChecker;
        this.productWithRecentPurchasesRepository = productWithRecentPurchasesRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public List<ProductWithRecentPurchases> retrieveMostPopularRecentlyPurchasedProductsOfUser(String username) {
        Assert.hasText(username);

        userExistenceChecker.checkIfUserExists(username);
        List<Integer> last5PurchasedProductIds = purchaseRepository.getNMostRecentByUsername(username, 5)
            .stream()
            .map(Purchase::getProductId).collect(Collectors.toList());

        List<ProductWithRecentPurchases> products = productWithRecentPurchasesRepository.getByIds(
            last5PurchasedProductIds
        );

        Comparator<ProductWithRecentPurchases> productComparator =
            (prod1, prod2) -> prod2.getRecent().length - prod1.getRecent().length;
        productComparator = productComparator.thenComparing((prod1, prod2) -> prod1.getId() - prod2.getId());
        products.sort(productComparator);

        return products;
    }
}
