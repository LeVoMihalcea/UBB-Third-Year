package Domain;

import java.util.ArrayList;
import java.util.List;

public class Decoder {
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

            image.setY(decodeSubmatrix(encodedY));
            image.setU(decodeSubmatrix(enlarge(encodedU)));
            image.setV(decodeSubmatrix(enlarge(encodedV)));

            image.convertToRGB();
            image.printToFile();

        } catch (Exception e) {
            System.out.println(e);
        }

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
