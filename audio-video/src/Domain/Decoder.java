package Domain;

import java.util.ArrayList;
import java.util.List;

public class Decoder {
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

    private List<Submatrix> encodedY;
    private List<Submatrix> encodedU;
    private List<Submatrix> encodedV;

    int width;
    int height;

    public Decoder(List<Submatrix> encodedY, List<Submatrix> encodedU, List<Submatrix> encodedV, int width, int height) {
        this.encodedY = encodedY;
        this.encodedU = encodedU;
        this.encodedV = encodedV;

        this.width = width;
        this.height = height;

        this.decode();
    }

    private void decode() {
        try {
            Image image = new Image(width, height);

            dequantization(encodedY);
            dequantization(encodedU);
            dequantization(encodedV);

            idct(encodedY);
            idct(encodedU);
            idct(encodedV);

            centerAround128(encodedY);
            centerAround128(encodedU);
            centerAround128(encodedV);

            image.setY(decodeSubmatrix(encodedY));
            image.setU(decodeSubmatrix(enlarge(encodedU)));
            image.setV(decodeSubmatrix(enlarge(encodedV)));

            image.convertToRGB();
            image.printToFile();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void dequantization(List<Submatrix> encoded) {
        for (Submatrix block: encoded)
            block.setData(MatrixHelper.compute(block.getData(), QUANTIZATION_TABLE));
    }

    private void idct(List<Submatrix> encoded) {
        for (Submatrix block: encoded)
            block.setData(inverseDCT(block.getData()));
    }

    private double[][] inverseDCT(double[][] data) {
        double[][] f = new double[8][8];
        double constant = (double) 1 / 4;

        for (int x = 0; x < 8; x++)
            for (int y = 0; y < 8; y++)
            {
                f[x][y] = (int) (constant * outerSum(data, x, y));
            }

        return f;
    }

    private double outerSum(double[][] matrix, int x, int y) {
        double sum = 0.0;
        for (int u = 0; u < 8; u++)
            sum += innerSum(matrix, x, y, u);
        return sum;
    }

    private double innerSum(double[][] matrix, int x, int y, int u) {
        double sum = 0.0;
        for (int v = 0; v < 8; v++)
            sum += product(matrix[u][v], x, y, u, v);
        return sum;
    }

    private double product(double matrixValue, int x, int y, int u, int v) {
        double cosU = Math.cos(
                ((2 * x + 1) * u * Math.PI) / 16
        );

        double cosV = Math.cos(
                ((2 * y + 1) * v * Math.PI) / 16
        );

        return alpha(u) * alpha(v) * matrixValue * cosU * cosV;
    }

    private double alpha(int value) {
        return value > 0 ? 1 : (1 / Math.sqrt(2.0));
    }

    private void centerAround128(List<Submatrix> encoded) {
        for (Submatrix block: encoded)
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++)
                    block.getData()[i][j] += 128.0;
    }

    private List<Submatrix> enlarge(List<Submatrix> encoded) {
        List<Submatrix> resized = new ArrayList<>();
        encoded.forEach(b -> resized.add(resizeBlock(b)));
        return resized;
    }

    private Submatrix resizeBlock(Submatrix submatrix) {
        Submatrix upsampledSubmatrix = new Submatrix(8, submatrix.getType());
        int line = 0;
        int column = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                double value = submatrix.getData()[i][j];
                upsampledSubmatrix.getData()[line][column] = value;
                upsampledSubmatrix.getData()[line][column + 1] = value;
                upsampledSubmatrix.getData()[line + 1][column] = value;
                upsampledSubmatrix.getData()[line + 1][column + 1] = value;
                column += 2;
            }
            line += 2;
            column = 0;
        }

        return upsampledSubmatrix;
    }

    private double[][] decodeSubmatrix(List<Submatrix> encoded) {
        double[][] matrix = new double[this.height][this.width];

        int line = 0;
        int column = 0;

        for (Submatrix submatrix : encoded) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    matrix[line + i][column + j] = submatrix.getData()[i][j];
                }
            }
            column += 8;
            if (column == width) {
                line += 8;
                column = 0;
            }
        }


        return matrix;
    }
}
