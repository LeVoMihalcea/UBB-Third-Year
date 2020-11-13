package Domain;

import java.io.*;
import java.util.Objects;

public class Image {
    private String fileName;
    private int imageWidth;
    private int imageHeight;
    private int maxValue;
    private int[][] r;
    private int[][] g;
    private int[][] b;
    //form 3 matrices: one for Y components, one for U components and one for V components
    private double[][] y;
    private double[][] u;
    private double[][] v;

    public Image(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        this.readImage(fileName);
        this.convertToYUV();
    }

    public Image(int width, int height) {
        r = new int[height][width];
        g = new int[height][width];
        b = new int[height][width];

        this.imageWidth = width;
        this.imageHeight = height;
    }

    private void convertToYUV() {
        //and convert each pixel value from RGB to YUV (use the arithmetic equations from the slides of the course)
        for (int line = 0; line < imageHeight; line++)
            for (int column = 0; column < imageWidth; column++) {
                y[line][column] = 0.299 * r[line][column] + 0.587 * g[line][column] + 0.114 * b[line][column];
                u[line][column] = 128 - 0.168736 * r[line][column] - 0.331264 * g[line][column] + 0.5 * b[line][column];
                v[line][column] = 128 + 0.5 * r[line][column] - 0.418688 * g[line][column] - 0.081312 * b[line][column];
            }
    }

    private void readImage(String fileName) {
        //read the Image image
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(this.fileName));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        String st;
        try {
            String firstLine = Objects.requireNonNull(br).readLine();
            br.readLine();

            String thirdLine = br.readLine();
            imageHeight = Integer.parseInt(thirdLine.split(" ")[1]);
            imageWidth = Integer.parseInt(thirdLine.split(" ")[0]);
            maxValue = Integer.parseInt(br.readLine());
            r = new int[imageHeight][imageWidth];
            g = new int[imageHeight][imageWidth];
            b = new int[imageHeight][imageWidth];

            y = new double[imageHeight][imageWidth];
            u = new double[imageHeight][imageWidth];
            v = new double[imageHeight][imageWidth];

            int line = 0;
            int column = 0;
            while ((st = br.readLine()) != null && line != imageHeight) {
                if (column == imageWidth) {
                    column = 0;
                    line++;
                }

                r[line][column] = Integer.parseInt(st);
                g[line][column] = Integer.parseInt(br.readLine());
                b[line][column] = Integer.parseInt(br.readLine());
                column++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int[][] getR() {
        return r;
    }

    public void setR(int[][] r) {
        this.r = r;
    }

    public int[][] getG() {
        return g;
    }

    public void setG(int[][] g) {
        this.g = g;
    }

    public int[][] getB() {
        return b;
    }

    public void setB(int[][] b) {
        this.b = b;
    }

    public double[][] getY() {
        return y;
    }

    public void setY(double[][] y) {
        this.y = y;
    }

    public double[][] getU() {
        return u;
    }

    public void setU(double[][] u) {
        this.u = u;
    }

    public double[][] getV() {
        return v;
    }

    public void setV(double[][] v) {
        this.v = v;
    }

    public void convertToRGB() {
        for (int line = 0; line < imageHeight; line++) {
            for (int column = 0; column < imageWidth; column++) {
                double R = y[line][column] + 1.402 * (v[line][column] - 128);
                double G = y[line][column] - 0.344136 * (u[line][column] - 128) - 0.714136 * (v[line][column] - 128);
                double B = y[line][column] + 1.7790 * (u[line][column] - 128);

                if (R > 255) R = 255.0;
                if (G > 255) G = 255.0;
                if (B > 255) B = 255.0;

                if (R < 0) R = 0.0;
                if (G < 0) G = 0.0;
                if (B < 0) B = 0.0;

                this.r[line][column] = (int) R;
                this.g[line][column] = (int) G;
                this.b[line][column] = (int) B;
            }
        }
    }

    public void printToFile() throws IOException {
        FileWriter fileWriter = new FileWriter("output.ppm");
        PrintWriter printWriter = new PrintWriter(fileWriter);

        printWriter.println("P3");
        printWriter.println("800 600");
        printWriter.println("255");
        for (int line = 0; line < imageHeight; line++) {
            for (int column = 0; column < imageWidth; column++) {
                printWriter.println(this.r[line][column]);
                printWriter.println(this.g[line][column]);
                printWriter.println(this.b[line][column]);
            }
        }

        printWriter.close();
    }
}
