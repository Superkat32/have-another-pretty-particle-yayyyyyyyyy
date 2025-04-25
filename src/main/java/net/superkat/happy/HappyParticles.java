package net.superkat.happy;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.superkat.happy.particle.BubbleParticleEffect;
import net.superkat.happy.particle.CloudParticleEffect;
import net.superkat.happy.particle.ExplosiveParticleEffect;
import net.superkat.happy.particle.JellyfishParticleEffect;
import net.superkat.happy.particle.SnailParticleEffect;

public class HappyParticles {
    public static final String MOD_ID = HaveAnotherPrettyParticleYayyyyyyyyy.MOD_ID;

    public static final ParticleType<JellyfishParticleEffect> JELLYFISH_PARTICLE = FabricParticleTypes.complex(JellyfishParticleEffect.CODEC, JellyfishParticleEffect.PACKET_CODEC);

    public static final ParticleType<CloudParticleEffect> CLOUD_PARTICLE = FabricParticleTypes.complex(true, CloudParticleEffect.CODEC, CloudParticleEffect.PACKET_CODEC);

    public static final ParticleType<BubbleParticleEffect> BUBBLE_PARTICLE = FabricParticleTypes.complex(BubbleParticleEffect.CODEC, BubbleParticleEffect.PACKET_CODEC);

    public static final ParticleType<SnailParticleEffect> SNAIL_PARTICLE = FabricParticleTypes.complex(SnailParticleEffect.CODEC, SnailParticleEffect.PACKET_CODEC);

    public static final SimpleParticleType PINK_SPARKLE = FabricParticleTypes.simple();

    public static final ParticleType<ExplosiveParticleEffect> EXPLOSIVE_PARTICLE = FabricParticleTypes.complex(ExplosiveParticleEffect.CODEC, ExplosiveParticleEffect.PACKET_CODEC);

    public static void register() {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "pink_sparkle"), PINK_SPARKLE);

        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "jellyfish"), JELLYFISH_PARTICLE);

        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "cloud"), CLOUD_PARTICLE);

        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "bubble"), BUBBLE_PARTICLE);

        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "snail"), SNAIL_PARTICLE);

        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "explosive_particle"), EXPLOSIVE_PARTICLE);
    }
}
