package app;

import transaction.Menu;

public class Program {
    public static void main(String[] args) {
        boolean developMod_ = false;
        if (args.length > 0) {
            for (String arg : args) {
                if (arg.equals("--profile=dev")) {
                    developMod_ = true;
                    break;
                }
            }
        }
        Menu menu = new Menu(developMod_);
        menu.start();
    }
}

