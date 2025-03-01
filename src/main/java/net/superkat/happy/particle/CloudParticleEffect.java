package net.superkat.happy.particle;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.dynamic.Codecs;
import net.superkat.happy.HappyParticles;
import net.superkat.happy.particle.defaults.AbstractScalableParticleEffect;
import org.joml.Vector3f;

public class CloudParticleEffect extends AbstractScalableParticleEffect {

    public static final MapCodec<CloudParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    SCALE_CODEC.optionalFieldOf("scale", CloudParticle.DEFAULT_SCALE).forGetter(effect -> effect.scale),
                    Codecs.POSITIVE_INT.optionalFieldOf("max_age", CloudParticle.DEFAULT_MAX_AGE).forGetter(effect -> effect.maxAge),
                    Codecs.POSITIVE_INT.optionalFieldOf("max_age_random", CloudParticle.DEFAULT_MAX_AGE_RANDOM).forGetter(effect -> effect.maxAgeRandom),
                    Codecs.POSITIVE_INT.optionalFieldOf("hits_until_fade", CloudParticle.DEFAULT_HITS_UNTIL_FADE).forGetter(effect -> effect.hitsUntilFade),
                    Codecs.POSITIVE_FLOAT.optionalFieldOf("fade_amount", CloudParticle.DEFAULT_FADE_AMOUNT).forGetter(effect -> effect.fadeAmount),
                    Codecs.VECTOR_3F.optionalFieldOf("velocity_after_hit", CloudParticle.DEFAULT_VELOCITY_AFTER_HIT).forGetter(effect -> effect.velocityAfterHit)
            ).apply(instance, CloudParticleEffect::new)
    );

    public static final PacketCodec<RegistryByteBuf, CloudParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, effect -> effect.scale,
            PacketCodecs.INTEGER, effect -> effect.maxAge,
            PacketCodecs.INTEGER, effect -> effect.maxAgeRandom,
            PacketCodecs.INTEGER, effect -> effect.hitsUntilFade,
            PacketCodecs.FLOAT, effect -> effect.fadeAmount,
            PacketCodecs.VECTOR3F, effect -> effect.velocityAfterHit,
            CloudParticleEffect::new
    );

    private final int maxAge;
    private final int maxAgeRandom;
    private final int hitsUntilFade;
    private final float fadeAmount;
    private final Vector3f velocityAfterHit;

    public CloudParticleEffect(float scale, int maxAge, int maxAgeRandom, int hitsUntilFade, float fadeAmount, Vector3f velocityAfterHit) {
        super(scale);
        this.maxAge = maxAge;
        this.maxAgeRandom = maxAgeRandom;
        this.hitsUntilFade = hitsUntilFade;
        this.fadeAmount = fadeAmount;
        this.velocityAfterHit = velocityAfterHit;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public int getMaxAgeRandom() {
        return maxAgeRandom;
    }

    public int getHitsUntilFade() {
        return hitsUntilFade;
    }

    public float getFadeAmount() {
        return fadeAmount;
    }

    public Vector3f getVelocityAfterHit() {
        return velocityAfterHit;
    }

    @Override
    public ParticleType<?> getType() {
        return HappyParticles.CLOUD_PARTICLE;
    }
}
