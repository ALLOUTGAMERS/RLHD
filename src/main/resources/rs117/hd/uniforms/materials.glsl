#include MATERIAL_COUNT

struct Material
{
    int diffuseMap;
    int normalMap;
    int displacementMap;
    int roughnessMap;
    int ambientOcclusionMap;
    int flowMap;
    float displacementScale;
    float specularStrength;
    float specularGloss;
    float flowMapStrength;
    float emissiveStrength;
    float pad;
    vec2 flowMapDuration;
    vec2 scrollDuration;
    vec2 textureScale;
};

layout(std140) uniform MaterialUniforms {
    Material MaterialArray[MATERIAL_COUNT];
};

#include MATERIAL_GETTER
