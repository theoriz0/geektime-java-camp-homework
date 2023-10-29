package io.github.theoriz0.herocat_example;

import io.github.theoriz0.herocat.HeroCatServer;

public class HeroCat {
    public static void main(String[] args) {
        HeroCatServer server = new HeroCatServer("io.github.theoriz0.herocat_example.webapp");
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
