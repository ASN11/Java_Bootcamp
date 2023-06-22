package src.java.app;

import src.java.logic.ImageColor;

import java.io.IOException;
import com.beust.jcommander.JCommander;

public class Program {

    public static void main(String[] args) throws IOException {
        ImageColor imageColor = new ImageColor();
        JCommander.newBuilder()
                .addObject(imageColor)
                .build()
                .parse(args);

        imageColor.paint();
    }
}
