package Domain;

import java.util.ArrayList;
import java.util.List;

public class MatrixHelper {
    static List<Submatrix> splitInSubmatrices(Image image, String type, double[][] matrix) {
        List<Submatrix> encoded = new ArrayList<>();

        for (int i = 0; i < image.getImageHeight(); i += 8)
            for (int j = 0; j < image.getImageWidth(); j += 8) {
                Submatrix data = getSubmatrix(type, i, j, matrix);

                if(type.equals("Y")){
                    //divide the Y matrix into blocks of 8x8 values; for each block store: the 64 values/bytes from the block,
                    //the type of block (Y) and the position of the block in the image
                    encoded.add(data);
                }
                else{
                    //divide the U and V matrixes into blocks of 8x8 values; each block stores: 4x4=16 values/bytes from the block
                    encoded.add(computeAverageForSubmatrix(data));
                }
            }

        return encoded;
    }

    private static Submatrix computeAverageForSubmatrix(Submatrix actualImage) {
        Submatrix data = new Submatrix(4, actualImage.getType());
        int line = 0;
        int column = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                data.getData()[i][j] = (actualImage.getData()[line][column] +
                        actualImage.getData()[line][column + 1] +
                        actualImage.getData()[line + 1][column] +
                        actualImage.getData()[line + 1][column + 1])
                        / 4.0;
                column += 2;
            }
            line += 2;
            column = 0;
        }
        return data;
    }

    private static Submatrix getSubmatrix(String type, int i, int j, double[][] matrix) {
        Submatrix data = new Submatrix(8, type);

        for(int i_index=0; i_index<8; i_index++){
            for(int j_index=0; j_index<8; j_index++){
                data.getData()[i_index][j_index] = matrix[i_index + i][j_index + j];
            }
        }
        return data;
    }

    public static void centerAround0(List<Submatrix> encoded) {
        for (Submatrix submatrix: encoded)
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++)
                    submatrix.getData()[i][j] -= 128.0;
    }

    public static double[][] computeCoefficients(double[][] data, double[][] quantization_table) {
        double[][] result = new double[8][8];
        double aux;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                aux = data[i][j] / quantization_table[i][j];
                if (aux < 0)
                    result[i][j] = (int) Math.ceil(aux);
                else
                    result[i][j] = (int) Math.floor(aux);
            }

        return result;
    }

    public static List<Submatrix> resize(List<Submatrix> encoded) {
        List<Submatrix> resized = new ArrayList<>();
        encoded.forEach(b -> resized.add(resizeBlock(b)));
        return resized;
    }

    private static Submatrix resizeBlock(Submatrix submatrix) {
        Submatrix result = new Submatrix(8,
                submatrix.getType());
        int line = 0;
        int column = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                double value = submatrix.getData()[i][j];
                result.getData()[line][column] = value;
                result.getData()[line][column + 1] = value;
                result.getData()[line + 1][column] = value;
                result.getData()[line + 1][column + 1] = value;
                column += 2;
            }
            line += 2;
            column = 0;
        }

        return result;
    }

    public static double[][] compute(double[][] data, double[][] quantization_table) {
        double[][] result = new double[8][8];

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                result[i][j] = (int) (data[i][j] * quantization_table[i][j]);

        return result;
    }

    private void centerAround128(List<Submatrix> encoded) {
        for (Submatrix block: encoded)
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++)
                    block.getData()[i][j] += 128.0;
    }
}
