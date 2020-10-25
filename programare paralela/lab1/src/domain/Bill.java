package domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bill implements Serializable {
    private Map<String, Integer> wantedProducts;

    public Bill() {
        wantedProducts = new HashMap<>();
    }

    public void addPurchasedProduct(String productName){
        if(!wantedProducts.containsKey(productName)){
            wantedProducts.put(productName, 1);
            return;
        }
        wantedProducts.put(productName, wantedProducts.get(productName) + 1);
    }

    public Map<String, Integer> getWantedProducts() {
        return wantedProducts;
    }

    public void setWantedProducts(Map<String, Integer> wantedProducts) {
        this.wantedProducts = wantedProducts;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "wantedProducts=" + wantedProducts +
                '}';
    }
}
