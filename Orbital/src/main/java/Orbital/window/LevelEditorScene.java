package Orbital.window;

import Orbital.inputs.KeyListener;
import org.lwjgl.BufferUtils;

import java.awt.event.KeyEvent;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20C.glCreateShader;

public class LevelEditorScene extends Scene {

    private String vertexShaderSrc = "#version 330 core\n" +
            "\n" +
            "layout (location=0) in vec3 aPos;\n" +
            "layout (location=1) in vec4 aColour;\n" +
            "\n" +
            "out vec4 fColour;\n" +
            "\n" +
            "void main() {\n" +
            "    fColour = aColour;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";


    private String fragmentShaderSrc = "#version 330 core\n" +
            "\n" +
            "in vec4 fColour;\n" +
            "\n" +
            "out vec4 colour;\n" +
            "\n" +
            "void main() {\n" +
            "    colour = fColour;\n" +
            "}";

    private int vertexID, fragmentID, shaderProgram;

    private float[] vertexArray = {
            // position               // color
            0.5f, -0.5f, 0.0f,       1.0f, 0.0f, 0.0f, 1.0f, // Bottom right 0
            -0.5f,  0.5f, 0.0f,       0.0f, 1.0f, 0.0f, 1.0f, // Top left     1
            0.5f,  0.5f, 0.0f ,      1.0f, 0.0f, 1.0f, 1.0f, // Top right    2
            -0.5f, -0.5f, 0.0f,       1.0f, 1.0f, 0.0f, 1.0f, // Bottom left  3
    };

    // IMPORTANT: MUST BE IN COUNTER CLOCKWISE ORDER
    private int[] elementArray = {
            /*
                  x        x
                  x        x
           */
            2, 1, 0, // Top right triangle
            0, 1, 3 // bottom left triangle
    };

    private int vaoID, vboID, eboID;

    public LevelEditorScene() {
        System.out.println("Inside level editor scene");
    }

    @Override
    public void update(float dt) {
        // Bind shader program
        glUseProgram(shaderProgram);
        // Bind the VAO that we're using
        glBindVertexArray(vaoID);

        // Enable the vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        glUseProgram(0);
    }

    @Override
    public void init() {
        // COMPILE AND LINK SHADERS

        // Load and compile the vertex shaders
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        // Pass the shader source to the GPU
        glShaderSource(vertexID, vertexShaderSrc);
        glCompileShader(vertexID);

        // Check for errors in the shader compilation process
        int success = glGetShaderi(vertexID, GL_COMPILE_AND_EXECUTE);

        if(success == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("[ORBITAL SHADER ERROR DEBUGGER] 'shader.glsl'\n\tVertex Shader compilation failed.");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }

        // Load and compile the fragment shaders
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        // Pass the shader source to the GPU
        glShaderSource(fragmentID, fragmentShaderSrc);
        glCompileShader(fragmentID);

        // Check for errors in the shader compilation process
        success = glGetShaderi(fragmentID, GL_COMPILE_AND_EXECUTE);

        if(success == GL_FALSE) {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("[ORBITAL SHADER ERROR DEBUGGER] 'shader.glsl'\n\tVertex Shader compilation failed.");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }


        // Link shaders and check for errors
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);

        // Check for linking errors
        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);

        if(success == GL_FALSE) {
            int len = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.out.println("[ORBITAL SHADER ERROR DEBUGGER] 'shader.glsl'\n\tShader linking failed");
            System.out.println(glGetProgramInfoLog(shaderProgram, len));
            assert false : "";
        }

        // GENERATE VAO'S, VBO'S AND EBO BUFFER OBJECTS AND SEND THE DATA TO THE GPU
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Create VBO upload the vertex buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Add the vertex attribute pointers
        int positionsSize = 3;
        int colorSize = 4;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * floatSizeBytes);
        glEnableVertexAttribArray(1);
    }
}
