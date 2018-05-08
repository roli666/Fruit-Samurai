#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;
layout (location=2) in vec3 vertexNormal;

out vec2 outTexCoord;

uniform mat4 projection_matrix;

void main()
{
    gl_Position = projection_matrix * vec4(position.x,position.y,1.0, 1.0);
    outTexCoord = texCoord;
}