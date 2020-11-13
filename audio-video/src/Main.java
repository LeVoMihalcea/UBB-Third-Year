import Domain.Decoder;
import Domain.Encoder;
import Domain.Image;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            Image image = new Image("./input.ppm");
            Encoder encoder = new Encoder(image);

            Decoder decoder = new Decoder(encoder.getEncodedY(), encoder.getEncodedU(), encoder.getEncodedV(),
                    image.getImageWidth(), image.getImageHeight());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
