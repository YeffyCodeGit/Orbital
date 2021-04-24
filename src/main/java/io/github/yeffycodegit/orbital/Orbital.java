package io.github.yeffycodegit.orbital;


/**
 * The core class for the Orbital Game Engine.
 *
 * @version 1.0.0
 * @author YeffyCodeGit
 */
public class Orbital {
    public Orbital(String title, int height, int width) {
        init(title, height, width);
    }

    public static void init(String title, int height, int width) {
        new Window(title, height, width).run();
    }
}
