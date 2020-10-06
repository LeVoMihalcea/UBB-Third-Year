package Runnables;

import Repository.ProductInventoryRepository;
import domain.Bill;
import domain.WantedProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductRunnable implements Runnable{



    private int numberOfRuns;
    private final Random random;
    private final ProductInventoryRepository repository;
    private final List<String> namesOfProducts;

    public ProductRunnable(ProductInventoryRepository repository, List<String> namesOfProducts) {
        this.repository = repository;
        this.namesOfProducts = namesOfProducts;
        random = new Random();
        this.numberOfRuns = random.nextInt() % 50;
        this.numberOfRuns = numberOfRuns < 0 ? numberOfRuns * -1 : numberOfRuns;
    }

    @Override
    public void run() {
        for(int runNumber=0; runNumber<numberOfRuns; runNumber++){
            List<WantedProduct> wantedProductList = generateListOfWantedProducts();
            List<String> boughtItems = new ArrayList<>();
            for(WantedProduct wantedProduct: wantedProductList){
                for(int i=0; i<wantedProduct.getQuantity(); i++){
                    Boolean transactionSucceeded =
                            this.repository.buyProduct(wantedProduct.getProductName());
                    if(transactionSucceeded){
                        boughtItems.add(wantedProduct.getProductName());
                    }
                    else{
                        break;
                    }
                }
            }
            if(boughtItems.size() > 0){
                this.createAndAddBillOfBoughtItems(boughtItems);
            }
        }
    }

    private void createAndAddBillOfBoughtItems(List<String> boughtItems) {
        Bill billToAdd = new Bill();
        for(String boughtProduct: boughtItems){
            billToAdd.addPurchasedProduct(boughtProduct);
        }
        this.repository.addBill(billToAdd);
    }

    private List<WantedProduct> generateListOfWantedProducts() {
        List<WantedProduct> wantedProductList = new ArrayList<>();

        for(String productName: namesOfProducts){
            WantedProduct wantedProduct =
                    new WantedProduct(productName, random.nextInt(10));
            wantedProductList.add(wantedProduct);
        }

        return wantedProductList;
    }
}
