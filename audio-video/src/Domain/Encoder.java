package Domain;

import java.util.List;

public class Encoder {
    private double[][] QUANTIZATION_TABLE = {
            {6, 4, 4, 6, 10, 16, 20, 24},
            {5, 5, 6, 8, 10, 23, 24, 22},
            {6, 5, 6, 10, 16, 23, 28, 22},
            {6, 7, 9, 12, 20, 35, 32, 25},
            {7, 9, 15, 22, 27, 44, 41, 31},
            {10, 14, 22, 26, 32, 42, 45, 37},
            {20, 26, 31, 35, 41, 48, 48, 40},
            {29, 37, 38, 39, 45, 40, 41, 40}
    };

    private Image image;
    private List<Submatrix> encodedY;
    private List<Submatrix> encodedU;
    private List<Submatrix> encodedV;

    public Encoder(Image image) {
        this.image = image;

        this.encodedY = MatrixHelper.splitInSubmatrices(image, "Y", image.getY());
        this.encodedU = MatrixHelper.splitInSubmatrices(image, "U", image.getU());
        this.encodedV = MatrixHelper.splitInSubmatrices(image, "V", image.getV());

        this.encodedU = MatrixHelper.resize(encodedU);
        this.encodedV = MatrixHelper.resize(encodedV);

        MatrixHelper.centerAround0(encodedY);
        MatrixHelper.centerAround0(encodedU);
        MatrixHelper.centerAround0(encodedV);

        dct(encodedY);
        dct(encodedU);
        dct(encodedV);

        quantization(encodedY);
        quantization(encodedU);
        quantization(encodedV);

        System.out.println(encodedY);
        System.out.println(encodedU);
        System.out.println(encodedV);
    }

    private void quantization(List<Submatrix> encoded) {
        for (Submatrix block: encoded)
            block.setData(MatrixHelper.computeCoefficients(block.getData(), QUANTIZATION_TABLE));
    }

    private static void dct(List<Submatrix> encoded) {
        for (Submatrix block: encoded)
            block.setData(forwardDCT(block.getData()));
    }

    private static double[][] forwardDCT(double[][] data) {
        double[][] encodedMatrix = new double[8][8];
        double constant = (double) 1 / 4;

        for (int u = 0; u < 8; u++)
            for (int v = 0; v < 8; v++)
            {
                encodedMatrix[u][v] = (int) (constant * alpha(u) * alpha(v) * outerSum(data, u, v));
            }

        return encodedMatrix;
    }

    private static double outerSum(double[][] data, int u, int v) {
        double sum = 0.0;
        for (int x = 0; x < 8; x++)
            sum += innerSum(data, u, v, x);
        return sum;
    }

    private static double innerSum(double[][] data, int u, int v, int x) {
        double sum = 0.0;
        for (int y = 0; y < 8; y++)
            sum += product(data[x][y], x, y, u, v);
        return sum;
    }

    private static double product(double matrixValue, int x, int y, int u, int v) {
        double cosU = Math.cos(((2 * x + 1) * u * Math.PI) / 16);

        double cosV = Math.cos(((2 * y + 1) * v * Math.PI) / 16);

        return matrixValue * cosU * cosV;
    }

    private static double alpha(int value) {
        return value > 0 ? 1 : (1 / Math.sqrt(2.0));
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Submatrix> getEncodedY() {
        return encodedY;
    }

    public void setEncodedY(List<Submatrix> encodedY) {
        this.encodedY = encodedY;
    }

    public List<Submatrix> getEncodedU() {
        return encodedU;
    }

    public void setEncodedU(List<Submatrix> encodedU) {
        this.encodedU = encodedU;
    }

    public List<Submatrix> getEncodedV() {
        return encodedV;
    }

    public void setEncodedV(List<Submatrix> encodedV) {
        this.encodedV = encodedV;
    }
}
