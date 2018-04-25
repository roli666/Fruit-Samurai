#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 inColour;

out vec3 exColour;

uniform mat4 projection_matrix;
uniform mat4 world_matrix;

void main()
{
	gl_Position = projection_matrix * world_matrix * vec4(position, 1.0);
	exColour = inColour;
}
