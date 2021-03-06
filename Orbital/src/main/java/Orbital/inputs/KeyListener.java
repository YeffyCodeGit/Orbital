package Orbital.inputs;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {

    private static KeyListener instance = null; // Singelton to make sure there is only one instance of the KeyListner class
    private boolean keyPressed[] = new boolean[350]; // A list of booleans, one for each button on the keyboard

    private KeyListener() {

    }

    public static KeyListener get() {
        if(instance == null) {
            instance = new KeyListener();
        }

        return instance;
    }


    // Callback to get which key is being pressed
    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if(action == GLFW_PRESS) {
            get().keyPressed[key] = true;
        } else if(action == GLFW_RELEASE) {
            get().keyPressed[key] = false;
        }
    }

    // Checking if a key is pressed or not
    public static boolean keyIsPressed(int keyCode) {
            return get().keyPressed[keyCode];
    }
}
