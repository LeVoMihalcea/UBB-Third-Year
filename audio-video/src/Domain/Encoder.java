package Domain;

import java.util.List;

public class Encoder {
    private Image image;
    private List<Submatrix> encodedY;
    private List<Submatrix> encodedU;
    private List<Submatrix> encodedV;

    public Encoder(Image image) {
        this.image = image;

        this.encodedY = MatrixHelper.splitInSubmatrices(image, "Y", image.getY());
        this.encodedU = MatrixHelper.splitInSubmatrices(image, "U", image.getU());
        this.encodedV = MatrixHelper.splitInSubmatrices(image, "V", image.getV());

        System.out.println(encodedY);
        System.out.println(encodedU);
        System.out.println(encodedV);
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
