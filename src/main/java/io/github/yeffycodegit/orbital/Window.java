package io.github.yeffycodegit.orbital;

import io.github.yeffycodegit.orbital.logger.LogLevel;
import io.github.yeffycodegit.orbital.logger.OrbitalLogger;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * The window class, containing all the functions to create a new window
 *
 * @author YeffyCodeGit
 * @version 1.0.0
 */
public class Window {
    private String title;
    private int width, height;

    private long glfwWindow; // The window handler

    OrbitalLogger logger = new OrbitalLogger();

    public Window(String title, int width, int height) {
         this.title = title;
         this.width = width;
         this.height = height;
    }

    public void run() {
        logger.log(LogLevel.SUCCESS, "Orbital is using LWJGL version " + Version.getVersion());
        logger.log(LogLevel.SUCCESS, "Orbital game engine is running successfully.");

        init();
        loop();
    }

    public void init() {
        // Set up a error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if(!glfwInit()) logger.log(LogLevel.ERROR, "Unable to initialize GLFW.");

        // Configure GLFW
        glfwDefaultWindowHints();

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the actual window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // This is important
        // I don't remember why tho
        GL.createCapabilities();
    }

    public void loop() {
        while(!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents();

            // Clear the screen colours
            glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(glfwWindow);
        }
    }
}
