package classes;

import interfaces.Printer;
import interfaces.Renderer;

public class PrinterWithPrefixImpl implements Printer {
    private String prefix;
    private final Renderer renderer;

    public PrinterWithPrefixImpl(Renderer renderer) {
        this.renderer = renderer;
        prefix = "";
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix + " ";
    }

    public void print(String text) {
        renderer.print(prefix + text);
    }
}
