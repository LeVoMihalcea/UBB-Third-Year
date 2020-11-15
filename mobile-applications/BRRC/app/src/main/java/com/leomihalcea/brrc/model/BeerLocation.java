package com.leomihalcea.brrc.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Objects;

public class BeerLocation implements Parcelable {
    private int id;
    private String beerName;
    private String beerType;
    private String pubName;
    private int price;

    public BeerLocation() {
    }

    public BeerLocation(int id, String beerName, String beerType, String pubName, int price) {
        this.id = id;
        this.beerName = beerName;
        this.beerType = beerType;
        this.pubName = pubName;
        this.price = price;
    }

    protected BeerLocation(Parcel in) {
        id = in.readInt();
        beerName = in.readString();
        beerType = in.readString();
        pubName = in.readString();
        price = in.readInt();
    }

    public static final Creator<BeerLocation> CREATOR = new Creator<BeerLocation>() {
        @Override
        public BeerLocation createFromParcel(Parcel in) {
            return new BeerLocation(in);
        }

        @Override
        public BeerLocation[] newArray(int size) {
            return new BeerLocation[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public String getBeerType() {
        return beerType;
    }

    public void setBeerType(String beerType) {
        this.beerType = beerType;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "BeerLocation{" +
                "id=" + id +
                ", beerName='" + beerName + '\'' +
                ", beerType='" + beerType + '\'' +
                ", pubName='" + pubName + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeerLocation that = (BeerLocation) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(beerName);
        dest.writeString(beerType);
        dest.writeString(pubName);
        dest.writeInt(price);
    }
}
