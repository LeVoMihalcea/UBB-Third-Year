package com.leomihalcea.brrc;

import com.leomihalcea.brrc.model.BeerLocation;

import java.util.ArrayList;

public class Repository {
    private static ArrayList<BeerLocation> beerLocations = new ArrayList<>();
    private static Repository INSTANCE;
    private static int currentId;

    private Repository() {
    }

    public static Repository getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Repository();
        }

        return INSTANCE;
    }

    static {
        beerLocations.add(new BeerLocation(1, "Nenea Iancu", "Blonde", "Klausen", 8));
        beerLocations.add(new BeerLocation(2, "Ginger Beard", "Ginger Beer", "Klausen", 20));
        beerLocations.add(new BeerLocation(3, "Zaganu", "Blonda", "Klausen", 15));

        currentId = 5;
    }

    public ArrayList<BeerLocation> getBeerLocations() {
        return beerLocations;
    }

    public boolean addBeerLocation(BeerLocation beerLocation){
        beerLocations.add(beerLocation);
        return true;
    }

    public boolean updateBeerLocation(BeerLocation newBeerLocation){
        for (BeerLocation beerLocation: beerLocations) {
            if(beerLocation.getId() == newBeerLocation.getId()) {
                beerLocation.setBeerName(newBeerLocation.getBeerName());
                beerLocation.setBeerType(newBeerLocation.getBeerType());
                beerLocation.setPubName(newBeerLocation.getPubName());
                beerLocation.setPrice(newBeerLocation.getPrice());
                return true;
            }
        }
        return false;
    }

    public boolean deleteBeerLocation(BeerLocation beerLocation){
        return beerLocations.remove(beerLocation);
    }

    public static int getCurrentId() {
        return currentId++;
    }
}
