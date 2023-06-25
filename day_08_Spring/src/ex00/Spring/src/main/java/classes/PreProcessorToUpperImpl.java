package classes;

import interfaces.PreProcessor;

public class PreProcessorToUpperImpl implements PreProcessor {
    public String processing(String text) {
        return text.toUpperCase();
    }
}
