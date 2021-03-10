package Orbital.window;

import Orbital.inputs.KeyListener;
import Orbital.inputs.MouseListner;
import Orbital.utill.Time;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private int width, height;  // Width and height variables for the window
    private String title;  // The title of the window
    private long glfwWindow; // The handler for the window

    float r, g, b, a;
    private boolean fadeToBlack = false;

    private static Window window = null; // Instance of the window

    private static Scene currentScene; // Scene object to get the current scene

    private Window() {
        // Setting the r, g, b values, height and width, and the title
        this.width = 1920;
        this.height = 1080;
        this.title = "Orbital Game Scene";
        r = 1;
        b = 1;
        g = 1;
        a = 1;
    }

    // Function to change the scene, taking in a index for which scene to change to
    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.init();
                break;
            case 1:
                currentScene = new LevelScene();
                currentScene.init();
                break;
            default:
                assert false : "[ORBITAL ERROR DEBUGGER] Unknown scene : '" + newScene + "."; // Throw a error
                break;
        }
    }

    // Function to get the instance for the window
    public static Window get() {
        // Checking if the window object is null
        if (Window.window == null) {
            Window.window = new Window(); // Creating a new window
        }

        return Window.window; // Return the window
    }


    public void run() {
        // Logging messages
        System.out.println("[ORBITAL SYSTEM DEBUGGER] Orbital Engine is functional.");
        System.out.println("[ORBITAL SYSTEM DEBUGGER] Orbital Engine is running LWJGL version " + Version.getVersion());

        // Call the init and loop functions
        init();
        loop();

        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and the free the error callbacks
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    public void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("[ORBITAL GLFW ERROR DEBUGGER] Orbital Engine failed to initialize GLFW.");
        }


        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        // If the window failed to create, throw a error
        if (glfwWindow == NULL) {
            throw new IllegalStateException("[ORBITAL GLFW ERROR DEBUGGER] Failed to create the GLFW window.");
        }

        // Set the input callbacks
        glfwSetCursorPosCallback(glfwWindow, MouseListner::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListner::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListner::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);


        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);
        // Make the window visible
        glfwShowWindow(glfwWindow);
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        Window.changeScene(0); // Change scene to a new leveleditor scene
    }

    public void loop() {
        float beginTime = Time.getTime(); // Get the time the program started from our time class
        float endTime; // The time the program stops
        float dt = -1.0f; // Delta time variable

        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            // If we want to fade to black
            if (fadeToBlack) {
                r = Math.max(r - 0.01f, 0);
                g = Math.max(g - 0.01f, 0);
                b = Math.max(b - 0.01f, 0);
            }

            if (dt >= 0) {
                currentScene.update(dt);
            }

            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
    }
}
