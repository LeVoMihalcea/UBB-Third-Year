package Repository;

import domain.Bill;
import domain.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ProductInventoryRepository implements Serializable {
    private final Map<String, Product> inventory;
    private final ReentrantLock productMutex;
    private final ReentrantLock billsMutex;
    private final List<Bill> billList;

    public ProductInventoryRepository() {
        this.inventory = new HashMap<>();
        this.productMutex = new ReentrantLock();
        this.billsMutex = new ReentrantLock();
        this.billList = new ArrayList<>();
    }

    public void addProduct(Product product){
        this.inventory.put(product.getName(), product);
    }

    public void restockProduct(String productName, Integer quantity){
        Product product = this.inventory.get(productName);
        product.setQuantity(product.getQuantity() + quantity);
    }

    public void addBill(Bill bill){
        try{
            billsMutex.lock();
            this.billList.add(bill);
        }
        finally {
            billsMutex.unlock();
        }
    }

    public Boolean buyProduct(String name){
        try{
            productMutex.lock();
            Product product = this.inventory.get(name);
            if(product.getQuantity() <= 0) {
                return false;
            }
            product.setQuantity(product.getQuantity() - 1);
        }
        finally {
            productMutex.unlock();
        }
        return true;
    }

    public Map<String, Product> getInventory() {
        return inventory;
    }

    public ReentrantLock getProductMutex() {
        return productMutex;
    }

    public ReentrantLock getBillsMutex() {
        return billsMutex;
    }

    public List<Bill> getBillList() {
        return billList;
    }

    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder();
        for(String productName: this.inventory.keySet()){
            returnString.append(productName).append(" ").append(inventory.get(productName)).append("\n");
        }
//        for(Bill bill: this.billList){
//            returnString.append(bill.toString()).append("\n");
//        }
        return returnString.toString();
    }
}
