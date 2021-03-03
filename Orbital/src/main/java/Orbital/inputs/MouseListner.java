package Orbital.inputs;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListner {
    private static MouseListner instance;
    private double xScroll, yScroll;
    private double xPos, yPos, lastY, lastX;
    private boolean pressedMouseButtons[] = new boolean[3];
    private boolean isDragging;

    private MouseListner() {
        this.xScroll = 0.0f;
        this.yScroll = 0.0f;
        this.xPos = 0.0f;
        this.yPos = 0.0f;
        this.lastX = 0.0f;
        this.lastY = 0.0f;
    }

    public static MouseListner get() {
        if(instance == null) {
            instance = new MouseListner();
        }

        return instance;
    }
    
    public static void mousePosCallback(long window, double xpos, double ypos) {
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xpos;
        get().yPos = ypos;
        get().isDragging = get().pressedMouseButtons[0] || get().pressedMouseButtons[1] || get().pressedMouseButtons[2];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            if (button < get().pressedMouseButtons.length) {
                get().pressedMouseButtons[button] = true;
            }
        } else if(action == GLFW_RELEASE) {
            if (button < get().pressedMouseButtons.length) {
                get().pressedMouseButtons[button] = false;
                get().isDragging = false;
            }
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        get().xScroll = xOffset;
        get().yScroll = yOffset;
    }

    public static void endFrame() {
        get().xScroll = 0;
        get().yScroll = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static float getX() {
        return (float)get().xPos;
    }

    public static float getY() {
        return (float)get().yPos;
    }

    public static float getDx() {
        return (float)(get().lastX - get().xPos);
    }

    public static float getDy() {
        return (float)(get().lastY - get().yPos);
    }

    public static float getScrollX() {
        return (float)get().xScroll;
    }

    public static float getScrollY() {
        return (float)get().yScroll;
    }

    public static boolean isDragging() {
        return get().isDragging;
    }

    public static boolean mouseButtonDown(int button) {
        if(button < get().pressedMouseButtons.length) {
            return get().pressedMouseButtons[button];
        } else {
            return false;
        }
    }
 }
