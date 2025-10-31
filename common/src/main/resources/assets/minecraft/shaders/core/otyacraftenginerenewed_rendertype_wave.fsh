#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec2 texCoord0;
in vec4 normal;
in float time;


out vec4 fragColor;

void main() {
    float t=time*1200.;
    float v=sin(texCoord0.x*3.+t);
    vec4 color = texture(Sampler0, texCoord0+ vec2(0., v));
    if (color.a < 0.1) {
        discard;
    }
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
