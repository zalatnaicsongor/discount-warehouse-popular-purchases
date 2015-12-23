package hu.zalatnai.discountwarehouse.products.infrastructure;

import hu.zalatnai.discountwarehouse.products.PurchaseContainer;
import hu.zalatnai.discountwarehouse.products.PurchasesConfiguration;
import hu.zalatnai.discountwarehouse.sdk.ListenableFutureProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

class PurchasesByProductIdFutureProvider implements ListenableFutureProvider<Integer, ResponseEntity<PurchaseContainer>> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final AsyncRestTemplate asyncRestTemplate;

    private final PurchasesConfiguration purchasesConfiguration;

    PurchasesByProductIdFutureProvider(
        AsyncRestTemplate asyncRestTemplate, PurchasesConfiguration purchasesConfiguration
    ) {
        this.asyncRestTemplate = asyncRestTemplate;
        this.purchasesConfiguration = purchasesConfiguration;
    }

    @Override
    public ListenableFuture<ResponseEntity<PurchaseContainer>> get(Integer source) {
        ListenableFuture<ResponseEntity<PurchaseContainer>> responseEntityListenableFuture =
            asyncRestTemplate.getForEntity(
                purchasesConfiguration.getBaseUrl() + "/" + purchasesConfiguration.getByProductPath() + "/" + source,
                PurchaseContainer.class
            );

        log.info("started retrieving PurchaseContainer " + source);
        responseEntityListenableFuture.addCallback(new ListenableFutureCallback<ResponseEntity<PurchaseContainer>>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(
                ResponseEntity<PurchaseContainer> purchaseContainerResponseEntity
            ) {
                log.info("completed retrieving PurchaseContainer " + source);
            }
        });

        return responseEntityListenableFuture;
    }
}
