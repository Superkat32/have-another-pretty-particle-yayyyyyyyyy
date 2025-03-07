package net.superkat.happy.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.dynamic.Codecs;
import net.superkat.happy.ColorUtil;
import net.superkat.happy.HappyParticles;
import net.superkat.happy.particle.defaults.AbstractScalableParticleEffect;
import org.joml.Vector3f;

import java.awt.*;

public class JellyfishParticleEffect extends AbstractScalableParticleEffect {
    public static final float DEFAULT_SCALE = 0.5f;
    public static final int DEFAULT_MAX_AGE = 60;
    public static final int DEFAULT_BOUNCES = 3;
    public static final int DEFAULT_COLOR_MODE_INDEX = JellyfishColorMode.DEFAULT.getIndex();

    public static final Vector3f DEFAULT_START = ColorUtil.colorToVector(Color.WHITE);
    public static final Vector3f DEFAULT_END = ColorUtil.colorToVector(new Color(195, 86, 234));

    public static final MapCodec<JellyfishParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    SCALE_CODEC.optionalFieldOf("scale", DEFAULT_SCALE).forGetter(effect -> effect.scale),
                    Codecs.POSITIVE_INT.optionalFieldOf("max_age", DEFAULT_MAX_AGE).forGetter(effect -> effect.maxAge),
                    Codecs.POSITIVE_INT.optionalFieldOf("bounces", DEFAULT_BOUNCES).forGetter(effect -> effect.bounces),
                    Codec.intRange(0, 2).optionalFieldOf("color_mode", DEFAULT_COLOR_MODE_INDEX).forGetter(effect -> effect.colorModeIndex),
                    Codecs.VECTOR_3F.optionalFieldOf("start_color", DEFAULT_START).forGetter(effect -> effect.startColor),
                    Codecs.VECTOR_3F.optionalFieldOf("end_color", DEFAULT_END).forGetter(effect -> effect.endColor)
            ).apply(instance, JellyfishParticleEffect::new)
    );

    public static final PacketCodec<RegistryByteBuf, JellyfishParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, effect -> effect.scale,
            PacketCodecs.INTEGER, effect -> effect.maxAge,
            PacketCodecs.INTEGER, effect -> effect.bounces,
            PacketCodecs.INTEGER, effect -> effect.colorModeIndex,
            PacketCodecs.VECTOR3F, effect -> effect.startColor,
            PacketCodecs.VECTOR3F, effect -> effect.endColor,
            JellyfishParticleEffect::new
    );

    private final int maxAge;
    private final int bounces;
    private final int colorModeIndex;
    private final Vector3f startColor;
    private final Vector3f endColor;

    public JellyfishParticleEffect(float scale, int maxAge, int bounces, int colorModeIndex, Vector3f startColor, Vector3f endColor) {
        super(scale);
        this.maxAge = maxAge;
        this.bounces = bounces;
        this.colorModeIndex = colorModeIndex;
        this.startColor = startColor;
        this.endColor = endColor;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public int getBounces() {
        return bounces;
    }

    public boolean isRandomColor() {
        return JellyfishColorMode.fromIndex(this.colorModeIndex) == JellyfishColorMode.RANDOM_COLOR;
    }

    public boolean isTransitionRandomColor() {
        return JellyfishColorMode.fromIndex(this.colorModeIndex) == JellyfishColorMode.TRANSITION_RANDOM;
    }

    public Vector3f getStartColor() {
        return startColor;
    }

    public Vector3f getEndColor() {
        return endColor;
    }

    @Override
    public ParticleType<?> getType() {
        return HappyParticles.JELLYFISH_PARTICLE;
    }

    public static enum JellyfishColorMode {
        DEFAULT, //do nothing basically
        RANDOM_COLOR, //random color using start/end colors as min/max, no transition
        TRANSITION_RANDOM; //random transition colors using start/end colors as min/max

        public int getIndex() {
            return this.ordinal();
        }

        public static JellyfishColorMode fromIndex(int index) {
            return values()[index];
        }
    }
}
