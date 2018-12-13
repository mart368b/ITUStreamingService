package maincomponents;

import reader.MediaHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageHandlerTest {

    public static void main(String[] args){
        ImageTest();
    }

    private static void ImageTest(){
        try {
            ImageHandler ih = ImageHandler.getInstance();
            ih.init();
            MediaHandler.getInstance();

            //Data set A
            assertEquals(ImageIO.read(new File("res/movies-images/12 Angry Men.jpg")),
                    ih.getImage("12 Angry Men"));

            //Data set B
            assertEquals(ih.getImage("stock"),
                    ih.getImage("Kage"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void assertEquals(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
            for (int x = 0; x < img1.getWidth(); x++) {
                for (int y = 0; y < img1.getHeight(); y++) {
                    if (img1.getRGB(x, y) != img2.getRGB(x, y))
                        System.err.println(img1 + " = " + img2);
                }
            }
        } else {
            System.err.println(img1 + " = " + img2);
        }
        System.out.println(img1 + " = " + img2);
    }
}
