package src.java.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.diogonunes.jcolor.Attribute;

import static com.diogonunes.jcolor.Ansi.colorize;

@Parameters(separators = "=")
public class ImageColor {

    @Parameter(names={"--white"})
    private String white;
    @Parameter(names={"--black"})
    private String black;
    private final URL inputImage = ImageColor.class.getResource("/resources/it.bmp");

    public ImageColor() {}

    /**
     * Считывает картинку и на основе полученной цветовой гаммы печатает в консоль копию с измененными цветами
     * @throws IOException ошибка считывания изображения
     */
    public void paint() throws IOException {
        BufferedImage image = ImageIO.read(Objects.requireNonNull(inputImage));

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int read_color = image.getRGB(x, y);
                Color color = getPixelColor(read_color);
                System.out.print(colorize("  ", Attribute.BACK_COLOR(color.getRed(), color.getGreen(), color.getBlue())));
            }
            System.out.println();
        }
    }

    /**
     * Принимает Строку с названием цвета и возвращает объект Color с этим цветом.
     * @param name строка с названием цвета
     * @return если цвет найден, то возвращает его, если нет, то по умолчанию возвращает черный цвет
     */
    public static Color getColorFromString(String name) {
        try {
            java.lang.reflect.Field field = Color.class.getField(name.toLowerCase());
            return (Color) field.get(null);
        } catch (Exception e) {
            return Color.BLACK;
        }
    }

    /**
     * @param color цвет пикселя, анализирует только 2 варианта, белый = -1, черный = -16777216
     * @return цвет, введенный пользователем, который пойдет на печать в консоль
     */
    private Color getPixelColor(int color) {
        if (color == -1) {
            return getColorFromString(white);
        } else if (color == -16777216) {
            return getColorFromString(black);
        }

        return Color.BLACK;
    }
}
