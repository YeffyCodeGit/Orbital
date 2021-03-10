#version 330 core

layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColour;

out vec4 fColour;

void main() {
    fColour = aColour;
    gl_Position = vec4(aPos, 1.0);
}

#version 330 core

out vec4 fColour;

out vec4 colour;

void main() {
    colour = fColour;
}