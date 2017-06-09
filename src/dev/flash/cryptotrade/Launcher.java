package dev.flash.cryptotrade;

/**
 * Created by Flash on 15/02/2017.
 */

public class Launcher {
    public static void main(String[] args) {
        Instance instance = new Instance("Crypto Trade", 1100, 600);
        instance.start();
    }
}

