#version 330

in vec2 outTexCoord;
out vec4 fragColor;

uniform sampler2D texture_sampler;
uniform vec3 color;
uniform vec4 ambient_light;
uniform int use_color;


void main()
{
	if(use_color==1)
	{
		fragColor = vec4(color, 1)*ambient_light;
	}

	else
	{
		fragColor = texture(texture_sampler, outTexCoord)*ambient_light;
		if (fragColor.w < 1.0)
        		discard;
	}
}