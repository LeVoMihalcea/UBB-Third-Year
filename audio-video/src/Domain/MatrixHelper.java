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
                data.getData()[i_index][j_index] = (int) matrix[i_index + i][j_index + j];
            }
        }
        return data;
    }
}
