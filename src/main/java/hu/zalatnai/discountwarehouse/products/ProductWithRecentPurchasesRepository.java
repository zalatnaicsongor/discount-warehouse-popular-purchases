package hu.zalatnai.discountwarehouse.products;

import java.util.Collection;
import java.util.List;

public interface ProductWithRecentPurchasesRepository {
    List<ProductWithRecentPurchases> getByIds(Collection<Integer> productIds);
}
