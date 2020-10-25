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
    private final ReentrantLock billsMutex;
    private final ReentrantLock moneyMutex;
    private final List<Bill> billList;

    private Long money;

    public ProductInventoryRepository() {
        this.inventory = new HashMap<>();
        this.billsMutex = new ReentrantLock();
        this.moneyMutex = new ReentrantLock();
        this.billList = new ArrayList<>();
        this.money = 0L;
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

    public Boolean buyProduct(String name) {
        return this.inventory.get(name).decreaseQuantity();
    }

    public Map<String, Product> getInventory() {
        return inventory;
    }

    public List<Bill> getBillList() {
        return billList;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
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
