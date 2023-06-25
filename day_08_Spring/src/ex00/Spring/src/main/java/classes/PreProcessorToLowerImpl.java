package classes;

import interfaces.PreProcessor;

public class PreProcessorToLowerImpl implements PreProcessor {
    public String processing(String text) {
        return text.toLowerCase();
    }
}
