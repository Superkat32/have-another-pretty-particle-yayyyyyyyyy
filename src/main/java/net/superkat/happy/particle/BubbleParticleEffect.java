package net.superkat.happy.particle;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.dynamic.Codecs;
import net.superkat.happy.HappyParticles;

public record BubbleParticleEffect(float scale, int maxAge, int maxAgeRandom) implements ParticleEffect {
    public static final float DEFAULT_SCALE = 0f;
    public static final int DEFAULT_MAX_AGE = 60;
    public static final int DEFAULT_MAX_AGE_RANDOM = 0;

    public static final MapCodec<BubbleParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codecs.POSITIVE_FLOAT.optionalFieldOf("scale", DEFAULT_SCALE).forGetter(effect -> effect.scale),
                    Codecs.POSITIVE_INT.optionalFieldOf("max_age", DEFAULT_MAX_AGE).forGetter(effect -> effect.maxAge),
                    Codecs.POSITIVE_INT.optionalFieldOf("max_age_random", DEFAULT_MAX_AGE_RANDOM).forGetter(effect -> effect.maxAgeRandom)
            ).apply(instance, BubbleParticleEffect::new)
    );

    public static final PacketCodec<RegistryByteBuf, BubbleParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, effect -> effect.scale,
            PacketCodecs.INTEGER, effect -> effect.maxAge,
            PacketCodecs.INTEGER, effect -> effect.maxAgeRandom,
            BubbleParticleEffect::new
    );

    @Override
    public ParticleType<?> getType() {
        return HappyParticles.BUBBLE_PARTICLE;
    }
}
