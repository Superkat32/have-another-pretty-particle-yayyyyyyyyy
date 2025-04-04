package net.superkat.happy.particle;

import com.mojang.serialization.Codec;
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

public class SnailParticleEffect extends AbstractScalableParticleEffect {
    public static final float DEFAULT_SCALE = 0f;
    public static final int DEFAULT_MAX_AGE = 200;
    public static final float DEFAULT_GRAVITY_STRENGTH = 1.25f;
    public static final Vector3f DEFAULT_MIN_COLOR = new Vector3f(0, 0, 0);
    public static final Vector3f DEFAULT_MAX_COLOR = new Vector3f(1, 1, 1);
    public static final float DEFAULT_SPEED = 0.015f;

    public static final MapCodec<SnailParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    SCALE_CODEC.optionalFieldOf("scale", DEFAULT_SCALE).forGetter(effect -> effect.scale),
                    Codecs.POSITIVE_INT.optionalFieldOf("max_age", DEFAULT_MAX_AGE).forGetter(effect -> effect.maxAge),
                    Codec.FLOAT.optionalFieldOf("gravity_strength", DEFAULT_GRAVITY_STRENGTH).forGetter(effect -> effect.gravityStrength),
                    Codec.FLOAT.optionalFieldOf("speed", DEFAULT_SPEED).forGetter(effect -> effect.speed),
                    Codecs.VECTOR_3F.optionalFieldOf("min_color", DEFAULT_MIN_COLOR).forGetter(effect -> effect.minColor),
                    Codecs.VECTOR_3F.optionalFieldOf("max_color", DEFAULT_MAX_COLOR).forGetter(effect -> effect.maxColor)
            ).apply(instance, SnailParticleEffect::new)
    );

    public static final PacketCodec<RegistryByteBuf, SnailParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, effect -> effect.scale,
            PacketCodecs.INTEGER, effect -> effect.maxAge,
            PacketCodecs.FLOAT, effect -> effect.gravityStrength,
            PacketCodecs.FLOAT, effect -> effect.speed,
            PacketCodecs.VECTOR3F, effect -> effect.minColor,
            PacketCodecs.VECTOR3F, effect -> effect.maxColor,
            SnailParticleEffect::new
    );

    private final int maxAge;
    private final float gravityStrength;
    private final float speed;
    private final Vector3f minColor;
    private final Vector3f maxColor;

    public SnailParticleEffect(float scale, int maxAge, float gravityStrength, float speed, Vector3f startColor, Vector3f endColor) {
        super(scale);
        this.maxAge = maxAge;
        this.gravityStrength = gravityStrength;
        this.speed = speed;
        this.minColor = startColor;
        this.maxColor = endColor;
    }

    @Override
    public ParticleType<?> getType() {
        return HappyParticles.SNAIL_PARTICLE;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public float getGravityStrength() {
        return gravityStrength;
    }

    public float getSpeed() {
        return speed;
    }

    public Vector3f getMinColor() {
        return minColor;
    }

    public Vector3f getMaxColor() {
        return maxColor;
    }
}
