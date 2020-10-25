package service;

import Repository.ProductInventoryRepository;
import Runnables.ProductRunnable;
import domain.Product;
import domain.Bill;
import utils.Copy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductService {
    private final static int THREAD_COUNT = 100;
    private final static int PRODUCT_PRICE_LIMIT = 1000;
    private final static int PRODUCT_QUANTITY_LIMIT = 10000;
    private final static int NUMBER_OF_CHECKS = 10;

    private final List<String> NAMES_OF_PRODUCTS = List.of("Laptop", "Mouse", "Telefon",
            "Mouse Pad", "Scaun", "Tastatura", "Cabluri");

    private final Random random;

    public ProductService() {
        this.random = new Random();
    }

    public void run(){
        ProductInventoryRepository repository = new ProductInventoryRepository();
        populateRepository(repository);
        ProductInventoryRepository repositoryClone = (ProductInventoryRepository) Copy.deepCopy(repository);

        List<Thread> threads = new ArrayList<>();

        long start = System.currentTimeMillis();

        for(int numberOfRuns=0; numberOfRuns<NUMBER_OF_CHECKS; numberOfRuns++){
            for(int i=0; i < THREAD_COUNT; i++){
                ProductRunnable productRunnable = new ProductRunnable(repository, NAMES_OF_PRODUCTS);
                Thread thread = new Thread(productRunnable);
                threads.add(thread);
                thread.start();
            }

            for(Thread thread: threads){
                try {
                    thread.join(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            checkBillsAndInventory(repository, repositoryClone);

        }

        checkBillsAndInventory(repository, repositoryClone);

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);

        System.out.println(repository);
    }

    private void checkBillsAndInventory(ProductInventoryRepository repository,
                                        ProductInventoryRepository repositoryClone) {
        ProductInventoryRepository auxiliaryRepository = (ProductInventoryRepository) Copy.deepCopy(repository);
        for(Bill bill: auxiliaryRepository.getBillList() ){
            for(String boughtProduct: bill.getWantedProducts().keySet()){
                auxiliaryRepository.restockProduct(boughtProduct, bill.getWantedProducts().get(boughtProduct));
            }
        }

        for(String productInInventory: auxiliaryRepository.getInventory().keySet()){
//            System.out.println(String.format("Product quantities do match! %s %s - %s %s",
//                    productInInventory,
//                    auxiliaryRepository.getInventory().get(productInInventory).getQuantity(),
//                    productInInventory,
//                    repositoryClone.getInventory().get(productInInventory).getQuantity()));
            if (auxiliaryRepository.getInventory().get(productInInventory).getQuantity() != repositoryClone.getInventory().get(productInInventory).getQuantity()){
                throw new RuntimeException(String.format("Product quantities do NOT match! %s %s - %s %s",
                        productInInventory,
                        auxiliaryRepository.getInventory().get(productInInventory).getQuantity(),
                        productInInventory,
                        repositoryClone.getInventory().get(productInInventory).getQuantity()));
            }
        }
//        System.out.println("Both inventories match");

        System.out.println("Final Profit:" + repository.getMoney());
    }

    private void populateRepository(ProductInventoryRepository repository) {
        for(String productName: NAMES_OF_PRODUCTS){
            int productPrice = random.nextInt(PRODUCT_PRICE_LIMIT);
            int productQuantity = random.nextInt(PRODUCT_QUANTITY_LIMIT);
            Product productToAdd = new Product(productName, productPrice, productQuantity);
            repository.addProduct(productToAdd);
        }
    }
}
