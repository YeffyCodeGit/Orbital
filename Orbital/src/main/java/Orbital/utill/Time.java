package Orbital.utill;

public class Time {
    public static float timeStarted = System.nanoTime(); // The time when the program starts

    public static float getTime() {
        return (float)((System.nanoTime() - timeStarted) * 1E-9); // Get the current time
    }
}
