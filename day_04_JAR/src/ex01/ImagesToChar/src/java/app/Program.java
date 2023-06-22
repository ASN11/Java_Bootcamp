package src.java.app;

import src.java.logic.ImageColor;

import java.io.IOException;


public class Program {
    public static void main(String[] args) throws IOException {

        ImageColor imageColor = new ImageColor(args);
        imageColor.paint();
    }
}
