package hu.zalatnai.discountwarehouse.popularpurchases;

import java.util.Arrays;
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
        1, ":)", 453, 234, new String[]{"a", "b"}
    );

    private ProductWithRecentPurchases productWithRecentPurchases2 = new ProductWithRecentPurchases(
        2, ":|", 234, 754, new String[]{"a"}
    );

    private ProductWithRecentPurchases productWithRecentPurchases3 = new ProductWithRecentPurchases(
        3, ":/", 456, 135, new String[]{"a", "b", "c", "d", "e"}
    );

    private ProductWithRecentPurchases productWithRecentPurchases4 = new ProductWithRecentPurchases(
        4, ":-", 123, 589, new String[]{"a", "b", "c"}
    );

    private ProductWithRecentPurchases productWithRecentPurchases5 = new ProductWithRecentPurchases(
        5, ":_", 890, 128, new String[]{"a", "b", "c", "d"}
    );

    @Before
    public void setUp() throws Exception {
        doThrow(new UserNotFoundException(nonExistentUsername))
            .when(userExistenceChecker)
            .checkIfUserExists(nonExistentUsername);

        when(purchaseRepository.getNMostRecentByUsername(existingUsername, 5)).thenReturn(
            Arrays.asList(
                new Purchase(9, 1, "u", "2013-03-13"),
                new Purchase(19, 2, "u", "2013-03-16"),
                new Purchase(29, 3, "u", "2013-03-10"),
                new Purchase(39, 4, "u", "2013-03-12"),
                new Purchase(49, 5, "u", "2013-03-13")
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
        verify(purchaseRepository).getNMostRecentByUsername(existingUsername, 5);
    }

    @Test
    public void getsTheFiveMostRecentlyBoughtProductsOfTheUser() {
        this.popularPurchasesService.retrieveMostPopularRecentlyPurchasedProductsOfUser(existingUsername);
        verify(productWithRecentPurchasesRepository).getByIds(this.productIds);
    }

    @Test
    public void returnsTheFiveMostRecentlyBoughtProductsOfTheUserSortedByTheNumberOfOtherUsersWhoBoughtTheSameProductInDescendingOrder() {
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