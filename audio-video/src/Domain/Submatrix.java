package Domain;

import java.util.Arrays;

public class Submatrix {
    private int size;
    private double[][] data;
    private String type;

    public Submatrix(int size, double[][] data, String type) {
        this.size = size;
        this.data = data;
        this.type = type;
    }

    public Submatrix(int size, String type) {
        this.size = size;
        this.type = type;
        this.data = new double[size][size];
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double[][] getData() {
        return data;
    }

    public void setData(double[][] data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder();

        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                toReturn.append(data[i][j]).append(" ");
            }
            toReturn.append("\n");
        }
        return toReturn.toString();
    }
}
