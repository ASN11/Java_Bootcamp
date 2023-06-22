package src.java.logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

public class ImageColor {
    private char white, black;
    private final URL inputImage = ImageColor.class.getResource("/resources/it.bmp");
    String[] args;

    public ImageColor(String[] args) {
        this.args = args;
        analyze(args);
    }

    public void paint() throws IOException {
        BufferedImage image = ImageIO.read(Objects.requireNonNull(inputImage));

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int color = image.getRGB(x, y);
                System.out.print(getPixelChar(color));
            }
            System.out.println();
        }
    }

    /**
     * @param color цвет пикселя, анализирует только 2 варианта, белый = -1, черный = -16777216
     * @return символ, введенный пользователем, который пойдет на печать в консоль
     */
    private char getPixelChar(int color) {
        if (color == -1) {
            return white;
        } else if (color == -16777216) {
            return black;
        }

        return ' ';
    }

    /**
     *  Считывает аргументы командной строки и заносит их в поля white, black, fileName
     *
     * @param args аргументы командной строки
     */
    private void analyze(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("--white=")) {
                if (arg.length() < 9) {
                    white = ' ';
                } else {
                    white = arg.charAt(8);
                }
            } else if (arg.startsWith("--black=")) {
                if (arg.length() < 9) {
                    black = ' ';
                } else {
                    black = arg.charAt(8);
                }
            }
        }
    }
}
