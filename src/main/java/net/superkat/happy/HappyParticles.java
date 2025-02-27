package net.superkat.happy;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.superkat.happy.particle.JellyfishParticleEffect;

public class HappyParticles {
    public static final String MOD_ID = HaveAnotherPrettyParticleYayyyyyyyyy.MOD_ID;
    public static final ParticleType<JellyfishParticleEffect> JELLYFISH_PARTICLE = FabricParticleTypes.complex(JellyfishParticleEffect.CODEC, JellyfishParticleEffect.PACKET_CODEC);
    public static final SimpleParticleType JELLYFISH_PARTICLE_SIMPLE = FabricParticleTypes.simple();

    public static void register() {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "jellyfish"), JELLYFISH_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "jellyfish_simple"), JELLYFISH_PARTICLE_SIMPLE);
    }
}
