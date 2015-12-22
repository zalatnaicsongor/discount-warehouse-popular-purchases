package hu.zalatnai.discountwarehouse.products;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "dw.purchases")
public class PurchasesConfiguration {
    private String baseUrl;

    private String byUserPath;

    private String byProductPath;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getByUserPath() {
        return byUserPath;
    }

    public void setByUserPath(String byUserPath) {
        this.byUserPath = byUserPath;
    }

    public String getByProductPath() {
        return byProductPath;
    }

    public void setByProductPath(String byProductPath) {
        this.byProductPath = byProductPath;
    }
}
