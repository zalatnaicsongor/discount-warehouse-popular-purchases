package hu.zalatnai.discountwarehouse.popularpurchases;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import hu.zalatnai.discountwarehouse.products.ProductWithRecentPurchases;
import hu.zalatnai.discountwarehouse.products.ProductWithRecentPurchasesRepository;
import hu.zalatnai.discountwarehouse.products.Purchase;
import hu.zalatnai.discountwarehouse.products.PurchaseRepository;
import hu.zalatnai.discountwarehouse.users.UserExistenceChecker;
import hu.zalatnai.discountwarehouse.users.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class PopularPurchasesServiceTest {
    private static final String nonExistentUsername = "nonExistentUsername";
    private static final String existingUsername = "existingUsername";

    @Mock
    private UserExistenceChecker userExistenceChecker;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private ProductWithRecentPurchasesRepository productWithRecentPurchasesRepository;

    @InjectMocks
    private PopularPurchasesService popularPurchasesService;

    private List<Integer> productIds = Arrays.asList(1, 2, 3, 4, 5);

    private ProductWithRecentPurchases productWithRecentPurchases1 = new ProductWithRecentPurchases(
        1, ":)", 453, 234, new HashSet<>(Arrays.asList("a", "b", existingUsername)), 3
    );

    private ProductWithRecentPurchases productWithRecentPurchases2 = new ProductWithRecentPurchases(
        2, ":|", 234, 754, Collections.singleton("a"), 1
    );

    private ProductWithRecentPurchases productWithRecentPurchases3 = new ProductWithRecentPurchases(
        3, ":/", 456, 135, new HashSet<>(Arrays.asList("a", "b", "c", "d", "e", existingUsername)), 11
    );

    private ProductWithRecentPurchases productWithRecentPurchases4 = new ProductWithRecentPurchases(
        4, ":-", 123, 589, new HashSet<>(Arrays.asList("a", "b", "c", existingUsername)), 7
    );

    private ProductWithRecentPurchases productWithRecentPurchases5 = new ProductWithRecentPurchases(
        5, ":_", 890, 128, new HashSet<>(Arrays.asList("a", "b", "c", "d", existingUsername)), 9
    );

    @Before
    public void setUp() throws Exception {
        doThrow(new UserNotFoundException(nonExistentUsername))
            .when(userExistenceChecker)
            .checkIfUserExists(nonExistentUsername);

        when(purchaseRepository.getFiveMostRecentByUsername(existingUsername)).thenReturn(
            Arrays.asList(
                new Purchase(9, 1, "u", "2013-03-13T12:48:21Z"),
                new Purchase(19, 2, "u", "2013-03-16T12:48:21Z"),
                new Purchase(29, 3, "u", "2013-03-10T12:48:21Z"),
                new Purchase(39, 4, "u", "2013-03-12T12:48:21Z"),
                new Purchase(49, 5, "u", "2013-03-13T12:48:21Z")
            )
        );

        when(productWithRecentPurchasesRepository.getByIds(this.productIds)).thenReturn(
            Arrays.asList(
                productWithRecentPurchases1,
                productWithRecentPurchases2,
                productWithRecentPurchases3,
                productWithRecentPurchases4,
                productWithRecentPurchases5
            )
        );
    }

    @Test
    public void checksIfAUserWithTheSuppliedUsernameIndeedExists() {
        this.popularPurchasesService.retrieveMostPopularRecentlyPurchasedProductsOfUser(existingUsername);
        verify(userExistenceChecker).checkIfUserExists(existingUsername);
    }

    @Test(expected = UserNotFoundException.class)
    public void throwsIfNoUserCouldBeFoundWithTheSuppliedUsername() {
        this.popularPurchasesService.retrieveMostPopularRecentlyPurchasedProductsOfUser(nonExistentUsername);
    }

    @Test
    public void getsTheFiveMostRecentPurchasesOfTheUser() {
        this.popularPurchasesService.retrieveMostPopularRecentlyPurchasedProductsOfUser(existingUsername);
        verify(purchaseRepository).getFiveMostRecentByUsername(existingUsername);
    }

    @Test
    public void getsTheFiveMostRecentlyBoughtProductsOfTheUser() {
        this.popularPurchasesService.retrieveMostPopularRecentlyPurchasedProductsOfUser(existingUsername);
        verify(productWithRecentPurchasesRepository).getByIds(this.productIds);
    }

    @Test
    public void filtersTheUsersOwnUsernameFromTheListOfRecentBuyers() {
        this.popularPurchasesService.retrieveMostPopularRecentlyPurchasedProductsOfUser(existingUsername);
        assertThat(productWithRecentPurchases1.getRecent(), not(hasItem(existingUsername)));
        assertThat(productWithRecentPurchases2.getRecent(), not(hasItem(existingUsername)));
        assertThat(productWithRecentPurchases3.getRecent(), not(hasItem(existingUsername)));
        assertThat(productWithRecentPurchases4.getRecent(), not(hasItem(existingUsername)));
        assertThat(productWithRecentPurchases5.getRecent(), not(hasItem(existingUsername)));
    }

    @Test
    public void returnsTheFiveMostRecentlyBoughtProductsOfTheUserSortedByTheNumberOfRecentPurchasesInDescendingOrder() {
        assertThat(
            this.popularPurchasesService.retrieveMostPopularRecentlyPurchasedProductsOfUser(existingUsername),
            contains(
                productWithRecentPurchases3,
                productWithRecentPurchases5,
                productWithRecentPurchases4,
                productWithRecentPurchases1,
                productWithRecentPurchases2
            )
        );
    }
}