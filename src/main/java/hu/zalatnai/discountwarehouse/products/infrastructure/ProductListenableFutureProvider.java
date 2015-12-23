package hu.zalatnai.discountwarehouse.products.infrastructure;

import hu.zalatnai.discountwarehouse.products.Product;
import hu.zalatnai.discountwarehouse.products.ProductsConfiguration;
import hu.zalatnai.discountwarehouse.sdk.ListenableFutureProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

class ProductListenableFutureProvider implements ListenableFutureProvider<Integer, ResponseEntity<Product>> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final AsyncRestTemplate asyncRestTemplate;

    private final ProductsConfiguration productsConfiguration;

    ProductListenableFutureProvider(AsyncRestTemplate asyncRestTemplate, ProductsConfiguration productsConfiguration) {
        this.asyncRestTemplate = asyncRestTemplate;
        this.productsConfiguration = productsConfiguration;
    }

    @Override
    public ListenableFuture<ResponseEntity<Product>> get(Integer source) {
        ListenableFuture<ResponseEntity<Product>> responseEntityListenableFuture =
            asyncRestTemplate.getForEntity(productsConfiguration.getBaseUrl() + "/" + source, Product.class);

        log.info("started retrieving Product " + source);
        responseEntityListenableFuture.addCallback(new ListenableFutureCallback<ResponseEntity<Product>>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(
                ResponseEntity<Product> purchaseContainerResponseEntity
            ) {
                log.info("completed retrieving Product " + source);
            }
        });

        return responseEntityListenableFuture;
    }
}
