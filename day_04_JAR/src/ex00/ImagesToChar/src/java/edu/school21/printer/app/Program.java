package src.java.edu.school21.printer.app;

import src.java.edu.school21.printer.logic.ImageColor;

import java.io.IOException;


public class Program {
    public static void main(String[] args) throws IOException {

        ImageColor imageColor = new ImageColor(args);
        imageColor.paint();
    }
}
