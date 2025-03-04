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

public class JellyfishParticleEffect extends AbstractScalableParticleEffect {

    public static final MapCodec<JellyfishParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    SCALE_CODEC.optionalFieldOf("scale", JellyfishParticle.DEFAULT_SCALE).forGetter(effect -> effect.scale),
                    Codecs.POSITIVE_INT.optionalFieldOf("max_age", JellyfishParticle.DEFAULT_MAX_AGE).forGetter(effect -> effect.maxAge),
                    Codecs.POSITIVE_INT.optionalFieldOf("bounces", JellyfishParticle.DEFAULT_BOUNCES).forGetter(effect -> effect.bounces),
                    Codecs.VECTOR_3F.optionalFieldOf("start_color", JellyfishParticle.DEFAULT_START).forGetter(effect -> effect.startColor),
                    Codecs.VECTOR_3F.optionalFieldOf("end_color", JellyfishParticle.DEFAULT_END).forGetter(effect -> effect.endColor)
            ).apply(instance, JellyfishParticleEffect::new)
    );

    public static final PacketCodec<RegistryByteBuf, JellyfishParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, effect -> effect.scale,
            PacketCodecs.INTEGER, effect -> effect.maxAge,
            PacketCodecs.INTEGER, effect -> effect.bounces,
            PacketCodecs.VECTOR3F, effect -> effect.startColor,
            PacketCodecs.VECTOR3F, effect -> effect.endColor,
            JellyfishParticleEffect::new
    );

    private final int maxAge;
    private final int bounces;
    private final Vector3f startColor;
    private final Vector3f endColor;
    public boolean randomColors = true;

    public JellyfishParticleEffect(float scale, int maxAge, int bounces, Vector3f startColor, Vector3f endColor) {
        super(scale);
        this.maxAge = maxAge;
        this.bounces = bounces;
        this.startColor = startColor;
        this.endColor = endColor;
        randomColors = false;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public int getBounces() {
        return bounces;
    }

    public Vector3f getStartColor() {
        return startColor;
    }

    public Vector3f getEndColor() {
        return endColor;
    }

    public boolean isRandomColors() {
        return randomColors;
    }

    @Override
    public ParticleType<?> getType() {
        return HappyParticles.JELLYFISH_PARTICLE;
    }
}
